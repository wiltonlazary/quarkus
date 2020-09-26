package io.quarkus.hibernate.orm.runtime.boot;

import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.PersistenceUnitTransactionType;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.jpa.boot.spi.PersistenceUnitDescriptor;

import io.quarkus.runtime.ObjectSubstitution;

/**
 * This represent the fully specified configuration of a Persistence Unit,
 * in a format which is compatible with the bytecode recorder.
 */
public final class QuarkusPersistenceUnitDefinition {

    private final LightPersistenceXmlDescriptor actualHibernateDescriptor;
    private final String dataSource;
    private final MultiTenancyStrategy multitenancyStrategy;
    private final boolean isReactive;

    public QuarkusPersistenceUnitDefinition(PersistenceUnitDescriptor persistenceUnitDescriptor, String dataSource,
            MultiTenancyStrategy multitenancyStrategy, boolean isReactive) {
        Objects.requireNonNull(persistenceUnitDescriptor);
        Objects.requireNonNull(multitenancyStrategy);
        this.actualHibernateDescriptor = LightPersistenceXmlDescriptor.validateAndReadFrom(persistenceUnitDescriptor);
        this.dataSource = dataSource;
        this.multitenancyStrategy = multitenancyStrategy;
        this.isReactive = isReactive;
    }

    /**
     * For bytecode deserialization
     */
    private QuarkusPersistenceUnitDefinition(LightPersistenceXmlDescriptor persistenceUnitDescriptor,
            String dataSource,
            MultiTenancyStrategy multitenancyStrategy,
            boolean isReactive) {
        Objects.requireNonNull(persistenceUnitDescriptor);
        Objects.requireNonNull(dataSource);
        Objects.requireNonNull(multitenancyStrategy);
        this.actualHibernateDescriptor = persistenceUnitDescriptor;
        this.dataSource = dataSource;
        this.multitenancyStrategy = multitenancyStrategy;
        this.isReactive = isReactive;
    }

    public PersistenceUnitDescriptor getActualHibernateDescriptor() {
        return actualHibernateDescriptor;
    }

    public String getName() {
        return actualHibernateDescriptor.getName();
    }

    public String getDataSource() {
        return dataSource;
    }

    public MultiTenancyStrategy getMultitenancyStrategy() {
        return multitenancyStrategy;
    }

    //TODO assert that we match the right type of ORM!
    public boolean isReactive() {
        return isReactive;
    }

    /**
     * This includes the state of both the QuarkusPersistenceUnitDefinition
     * and its more complex field of type LightPersistenceXmlDescriptor
     */
    public static class Serialized {

        private String dataSource;
        private MultiTenancyStrategy multitenancyStrategy;
        private boolean isReactive;
        private String puName;
        private String puProviderClassName;
        private boolean puUseQuotedIdentifiers;
        private PersistenceUnitTransactionType puTransactionType;
        private ValidationMode puValidationMode;
        private SharedCacheMode puSharedCachemode;
        private List<String> puManagedClassNames;
        private Properties puProperties;

        //All standard getters and setters generated by IDE:

        public String getDataSource() {
            return dataSource;
        }

        public void setDataSource(String dataSource) {
            this.dataSource = dataSource;
        }

        public String getPuName() {
            return puName;
        }

        public void setPuName(String puName) {
            this.puName = puName;
        }

        public MultiTenancyStrategy getMultitenancyStrategy() {
            return multitenancyStrategy;
        }

        public void setMultitenancyStrategy(MultiTenancyStrategy multitenancyStrategy) {
            this.multitenancyStrategy = multitenancyStrategy;
        }

        public boolean isReactive() {
            return isReactive;
        }

        public void setReactive(boolean reactive) {
            isReactive = reactive;
        }

        public String getPuProviderClassName() {
            return puProviderClassName;
        }

        public void setPuProviderClassName(String puProviderClassName) {
            this.puProviderClassName = puProviderClassName;
        }

        public boolean isPuUseQuotedIdentifiers() {
            return puUseQuotedIdentifiers;
        }

        public void setPuUseQuotedIdentifiers(boolean puUseQuotedIdentifiers) {
            this.puUseQuotedIdentifiers = puUseQuotedIdentifiers;
        }

        public PersistenceUnitTransactionType getPuTransactionType() {
            return puTransactionType;
        }

        public void setPuTransactionType(PersistenceUnitTransactionType puTransactionType) {
            this.puTransactionType = puTransactionType;
        }

        public ValidationMode getPuValidationMode() {
            return puValidationMode;
        }

        public void setPuValidationMode(ValidationMode puValidationMode) {
            this.puValidationMode = puValidationMode;
        }

        public SharedCacheMode getPuSharedCachemode() {
            return puSharedCachemode;
        }

        public void setPuSharedCachemode(SharedCacheMode puSharedCachemode) {
            this.puSharedCachemode = puSharedCachemode;
        }

        public List<String> getPuManagedClassNames() {
            return puManagedClassNames;
        }

        public void setPuManagedClassNames(List<String> puManagedClassNames) {
            this.puManagedClassNames = puManagedClassNames;
        }

        public Properties getPuProperties() {
            return puProperties;
        }

        public void setPuProperties(Properties puProperties) {
            this.puProperties = puProperties;
        }
    }

    public static final class Substitution implements ObjectSubstitution<QuarkusPersistenceUnitDefinition, Serialized> {

        @Override
        public Serialized serialize(final QuarkusPersistenceUnitDefinition obj) {
            final Serialized s = new Serialized();
            //First, fields from LightPersistenceXmlDescriptor:
            s.setPuName(obj.actualHibernateDescriptor.getName());
            s.setPuProviderClassName(obj.actualHibernateDescriptor.getProviderClassName());
            s.setPuUseQuotedIdentifiers(obj.actualHibernateDescriptor.isUseQuotedIdentifiers());
            s.setPuTransactionType(obj.actualHibernateDescriptor.getTransactionType());
            s.setPuValidationMode(obj.actualHibernateDescriptor.getValidationMode());
            s.setPuSharedCachemode(obj.actualHibernateDescriptor.getSharedCacheMode());
            s.setPuManagedClassNames(obj.actualHibernateDescriptor.getManagedClassNames());
            s.setPuProperties(obj.actualHibernateDescriptor.getProperties());
            //Remaining fields of QuarkusPersistenceUnitDefinition
            s.setDataSource(obj.getDataSource());
            s.setMultitenancyStrategy(obj.getMultitenancyStrategy());
            s.setReactive(obj.isReactive);
            return s;
        }

        @Override
        public QuarkusPersistenceUnitDefinition deserialize(Serialized obj) {
            LightPersistenceXmlDescriptor xmlDescriptor = new LightPersistenceXmlDescriptor(
                    obj.puName, obj.puProviderClassName, obj.puUseQuotedIdentifiers, obj.puTransactionType,
                    obj.puValidationMode, obj.puSharedCachemode, obj.puManagedClassNames, obj.puProperties);

            return new QuarkusPersistenceUnitDefinition(xmlDescriptor, obj.getDataSource(), obj.getMultitenancyStrategy(),
                    obj.isReactive());
        }
    }

}
