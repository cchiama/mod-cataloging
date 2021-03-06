package org.folio.cataloging.dao;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.Type;
import org.folio.cataloging.business.common.DataAccessException;
import org.folio.cataloging.business.common.Persistence;
import org.folio.cataloging.business.common.ReferentialIntegrityException;
import org.folio.cataloging.business.common.View;
import org.folio.cataloging.dao.persistence.*;

import java.util.List;

/**
 * Manages headings in the NME_HDG table.
 *
 * @author paulm
 * @author carment
 */
public class NameDescriptorDAO extends DAODescriptor
{

    /**
     * Gets the persistent class.
     *
     * @return the persistent class
     */

    public Class getPersistentClass() {
        return NME_HDG.class;
    }

    /**
     * Supports authorities.
     *
     * @return true, if successful
     */
    public boolean supportsAuthorities() {
        return true;
    }

    /**
     * Gets the cross reference count.
     *
     * @param source the source
     * @param cataloguingView the cataloguing view
     * @return the xref count
     * @throws DataAccessException the data access exception
     */
    @SuppressWarnings("unchecked")
    public int getXrefCount(final Descriptor source, final int cataloguingView, final Session session)
            throws HibernateException {

        int count = super.getXrefCount(source, cataloguingView, session);
        List<Integer> countList = session.find(
                "select count(*) from NME_NME_TTL_REF as ref "
                        + " where ref.nameHeadingNumber = ? "
                        + " and ref.sourceHeadingType = 'NH' "
                        + " and substr(ref.userViewString, ?, 1) = '1'",
                new Object[] {
                        source.getKey().getHeadingNumber(),
                        cataloguingView},
                new Type[] {
                        Hibernate.INTEGER,
                        Hibernate.INTEGER });
        count = count + countList.get(0);
        countList =
                session.find(
                        "select count(*) from NME_TO_TTL_REF as ref "
                                + " where ref.nameHeadingNumber = ? "
                                + " and ref.sourceHeadingType = 'NH' "
                                + " and substr(ref.userViewString, ?, 1) = '1'",
                        new Object[] {
                                source.getKey().getHeadingNumber(),
                                cataloguingView},
                        new Type[] {
                                Hibernate.INTEGER,
                                Hibernate.INTEGER });
        count = count + countList.get(0);
        return count;
    }

    /**
     * Gets the cross references.
     *
     * @param source the source
     * @param cataloguingView the cataloguing view
     * @param session the session
     * @return the cross references
     * @throws HibernateException the hibernate exception
     */
    @SuppressWarnings("unchecked")
    public List<REF> getCrossReferences(final Descriptor source, final int cataloguingView, final Session session)
            throws HibernateException {

        List<REF> refList = super.getCrossReferences(source, cataloguingView, session);

        refList.addAll(
                find(
                        "from NME_NME_TTL_REF as ref "
                                + " where ref.nameHeadingNumber = ? "
                                + " and ref.sourceHeadingType = 'NH' "
                                + " and substr(ref.userViewString, ?, 1) = '1'",
                        new Object[] {
                                source.getKey().getHeadingNumber(),
                                cataloguingView},
                        new Type[]
                                { Hibernate.INTEGER,
                                        Hibernate.INTEGER }));

        refList.addAll(
                find(
                        "from NME_TO_TTL_REF as ref "
                                + " where ref.nameHeadingNumber = ? "
                                + " and ref.sourceHeadingType = 'NH' "
                                + " and substr(ref.userViewString, ?, 1) = '1'",
                        new Object[] {
                                source.getKey().getHeadingNumber(),
                                cataloguingView},
                        new Type[] {
                                Hibernate.INTEGER,
                                Hibernate.INTEGER }));
        return refList;
    }

    /**
     * Load cross reference.
     *
     * @param source the source
     * @param target the target
     * @param referenceType the reference type
     * @param cataloguingView the cataloguing view
     * @return the ref
     * @throws DataAccessException the data access exception
     */
       public REF loadReference(
            final Descriptor source,
            final Descriptor target,
            final short referenceType,
            final int cataloguingView,
            final Session session)
            throws HibernateException {

        final String nameToTitle = new StringBuilder()
                .append("from NME_TO_TTL_REF as ref ")
                .append(" where ref.nameHeadingNumber = ? AND ")
                .append(" ref.titleHeadingNumber = ? AND ")
                .append(" ref.sourceHeadingType = 'NH' AND ")
                .append(" substr(ref.userViewString, ?, 1) = '1' AND ")
                .append(" ref.type = ?").toString();

        final String nameNameTitle = new StringBuilder()
                .append("from NME_NME_TTL_REF as ref ")
                .append(" where ref.nameHeadingNumber = ? AND ")
                .append(" ref.nameTitleHeadingNumber = ? AND ")
                .append(" ref.sourceHeadingType = 'NH' AND ")
                .append(" substr(ref.userViewString, ?, 1) = '1' AND ")
                .append( " ref.type = ").toString();

        if (source.getClass() == target.getClass()) {
            return super.loadReference(source, target, referenceType, cataloguingView, session);
        } else if (target.getClass() == TTL_HDG.class) {
            return loadReferenceByQuery(source, target, referenceType, cataloguingView, nameToTitle, session);
        } else {
            return loadReferenceByQuery(source, target, referenceType, cataloguingView, nameNameTitle, session);
        }
    }


    /**
     * Delete NME_HDG.
     *
     * @param p the p
     * @param session the session
     * @throws ReferentialIntegrityException the referential integrity exception
     * @throws HibernateException the hibernate exception
     */
    @SuppressWarnings("unchecked")
    public void delete(final Persistence p, final Session session)
            throws ReferentialIntegrityException, HibernateException {

        final NME_HDG nameHeading = (NME_HDG) p;
        final List<Integer> countList =
                session.find(
                        "select count(*) from NME_TTL_HDG as t where "
                                + " t.nameHeadingNumber = ? and "
                                + " substr(t.key.userViewString, ?, 1) = '1'",
                        new Object[] {
                                nameHeading.getKey().getHeadingNumber(),
                                View.toIntView(nameHeading.getUserViewString())},
                        new Type[] {
                                Hibernate.INTEGER,
                                Hibernate.INTEGER });
        if (countList.get(0) > 0) {
            throw new ReferentialIntegrityException("NME_TTL_HDG", "NME_HDG");
        }
        p.markDeleted();
        persistByStatus(p, session);
    }

    /**
     * Checks if is matching another heading(NME_HDG).
     *
     * @param desc the desc
     * @param session the session
     * @return true, if is matching another heading
     * @throws HibernateException the hibernate exception
     */
    @SuppressWarnings("unchecked")
    public boolean isMatchingAnotherHeading(final Descriptor desc, final Session session)
            throws  HibernateException {
        final NME_HDG nameHeading = (NME_HDG) desc;
        final List<NME_HDG> nameHeadingList = session.find(  " from "
                            + getPersistentClass().getName()
                            + " as c "
                            + " where c.stringText= ? "
                            + " and c.indexingLanguage = ? "
                            + " and c.accessPointLanguage = ?"
                            + " and c.typeCode =? "
                            + " and c.subTypeCode =? "
                            + " and c.key.userViewString = ?"
                            + " and c.key.headingNumber <> ?",
                    new Object[] {
                            nameHeading.getStringText(),
                            nameHeading.getIndexingLanguage(),
                            nameHeading.getAccessPointLanguage(),
                            nameHeading.getTypeCode(),
                            nameHeading.getSubTypeCode(),
                            nameHeading.getUserViewString(),
                            nameHeading.getKey().getHeadingNumber() },
                    new Type[] { Hibernate.STRING,
                            Hibernate.INTEGER,
                            Hibernate.INTEGER,
                            Hibernate.INTEGER,
                            Hibernate.INTEGER,
                            Hibernate.STRING,
                            Hibernate.INTEGER});
            nameHeadingList.stream().forEach((NME_HDG descriptor) ->
                    compareHeading(nameHeading, descriptor));
            return false;
    }

    /**
     * Compare the headings by authority source.
     *
     * @param descriptorFrom the heading to insert
     * @param descriptorTo descriptor already present
     * @return true, if successful
     */
    //TODO: to check
    private boolean compareHeading(Descriptor descriptorFrom, Descriptor descriptorTo) {
        if (descriptorFrom.getAuthoritySourceCode() == descriptorTo.getAuthoritySourceCode()) {
            if (descriptorFrom.getAuthoritySourceCode() == T_AUT_HDG_SRC.SOURCE_IN_SUBFIELD_2) {
                if (descriptorFrom.getAuthoritySourceText().equals(descriptorTo.getAuthoritySourceText())) {
                    return true;
                }
            }
            else {
                return true;
            }
        }
        return false;
    }

}
