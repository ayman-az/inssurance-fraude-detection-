// ============================================================================
//
// Copyright (c) 2006-2015, Talend SA
//
// Ce code source a été automatiquement généré par_Talend Open Studio for Data Integration
// / Soumis à la Licence Apache, Version 2.0 (la "Licence") ;
// votre utilisation de ce fichier doit respecter les termes de la Licence.
// Vous pouvez obtenir une copie de la Licence sur
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Sauf lorsqu'explicitement prévu par la loi en vigueur ou accepté par écrit, le logiciel
// distribué sous la Licence est distribué "TEL QUEL",
// SANS GARANTIE OU CONDITION D'AUCUNE SORTE, expresse ou implicite.
// Consultez la Licence pour connaître la terminologie spécifique régissant les autorisations et
// les limites prévues par la Licence.

package businessintelligenceminiprojet.csv_to_db_0_1;

import routines.Numeric;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.StringHandling;
import routines.Relational;
import routines.TalendDate;
import routines.Mathematical;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;

@SuppressWarnings("unused")

/**
 * Job: csv_to_db Purpose: <br>
 * Description: <br>
 * 
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status
 */
public class csv_to_db implements TalendJob {

	protected static void logIgnoredError(String message, Throwable cause) {
		System.err.println(message);
		if (cause != null) {
			cause.printStackTrace();
		}

	}

	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

	private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

	private final static String utf8Charset = "UTF-8";

	// contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

		public PropertiesWithType(java.util.Properties properties) {
			super(properties);
		}

		public PropertiesWithType() {
			super();
		}

		public void setContextType(String key, String type) {
			propertyTypes.put(key, type);
		}

		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}

	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();

	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public void synchronizeContext() {

		}

		// if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

	}

	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

	public ContextProperties getContext() {
		return this.context;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "csv_to_db";
	private final String projectName = "BUSINESSINTELLIGENCEMINIPROJET";
	public Integer errorCode = null;
	private String currentComponent = "";

	private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

	private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	private RunStat runStat = new RunStat();

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(),
					new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	public void setDataSourceReferences(List serviceReferences) throws Exception {

		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();

		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
				.getServices(serviceReferences, javax.sql.DataSource.class).entrySet()) {
			dataSources.put(entry.getKey(), entry.getValue());
			talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
		}

		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

	public String getExceptionStackTrace() {
		if ("failure".equals(this.getStatus())) {
			errorMessagePS.flush();
			return baos.toString();
		}
		return null;
	}

	private Exception exception;

	public Exception getException() {
		if ("failure".equals(this.getStatus())) {
			return this.exception;
		}
		return null;
	}

	private class TalendException extends Exception {

		private static final long serialVersionUID = 1L;

		private java.util.Map<String, Object> globalMap = null;
		private Exception e = null;
		private String currentComponent = null;
		private String virtualComponentName = null;

		public void setVirtualComponentName(String virtualComponentName) {
			this.virtualComponentName = virtualComponentName;
		}

		private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
			this.currentComponent = errorComponent;
			this.globalMap = globalMap;
			this.e = e;
		}

		public Exception getException() {
			return this.e;
		}

		public String getCurrentComponent() {
			return this.currentComponent;
		}

		public String getExceptionCauseMessage(Exception e) {
			Throwable cause = e;
			String message = null;
			int i = 10;
			while (null != cause && 0 < i--) {
				message = cause.getMessage();
				if (null == message) {
					cause = cause.getCause();
				} else {
					break;
				}
			}
			if (null == message) {
				message = e.getClass().getName();
			}
			return message;
		}

		@Override
		public void printStackTrace() {
			if (!(e instanceof TalendException || e instanceof TDieException)) {
				if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
					globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				}
				globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
			}
			if (!(e instanceof TDieException)) {
				if (e instanceof TalendException) {
					e.printStackTrace();
				} else {
					e.printStackTrace();
					e.printStackTrace(errorMessagePS);
					csv_to_db.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(csv_to_db.this, new Object[] { e, currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
					}
				} catch (Exception e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void tDBInput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileOutputMSDelimited_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputDelimited_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tFileInputDelimited_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db = new byte[0];
		static byte[] commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public Integer months_as_customer;

		public Integer getMonths_as_customer() {
			return this.months_as_customer;
		}

		public Integer age;

		public Integer getAge() {
			return this.age;
		}

		public Integer policy_number;

		public Integer getPolicy_number() {
			return this.policy_number;
		}

		public java.util.Date policy_bind_date;

		public java.util.Date getPolicy_bind_date() {
			return this.policy_bind_date;
		}

		public String policy_state;

		public String getPolicy_state() {
			return this.policy_state;
		}

		public String policy_csl;

		public String getPolicy_csl() {
			return this.policy_csl;
		}

		public Integer policy_deductable;

		public Integer getPolicy_deductable() {
			return this.policy_deductable;
		}

		public Float policy_annual_premium;

		public Float getPolicy_annual_premium() {
			return this.policy_annual_premium;
		}

		public Integer umbrella_limit;

		public Integer getUmbrella_limit() {
			return this.umbrella_limit;
		}

		public Integer insured_zip;

		public Integer getInsured_zip() {
			return this.insured_zip;
		}

		public String insured_sex;

		public String getInsured_sex() {
			return this.insured_sex;
		}

		public String insured_education_level;

		public String getInsured_education_level() {
			return this.insured_education_level;
		}

		public String insured_occupation;

		public String getInsured_occupation() {
			return this.insured_occupation;
		}

		public String insured_hobbies;

		public String getInsured_hobbies() {
			return this.insured_hobbies;
		}

		public String insured_relationship;

		public String getInsured_relationship() {
			return this.insured_relationship;
		}

		public Integer capital_gains;

		public Integer getCapital_gains() {
			return this.capital_gains;
		}

		public Integer capital_loss;

		public Integer getCapital_loss() {
			return this.capital_loss;
		}

		public java.util.Date incident_date;

		public java.util.Date getIncident_date() {
			return this.incident_date;
		}

		public String incident_type;

		public String getIncident_type() {
			return this.incident_type;
		}

		public String collision_type;

		public String getCollision_type() {
			return this.collision_type;
		}

		public String incident_severity;

		public String getIncident_severity() {
			return this.incident_severity;
		}

		public String authorities_contacted;

		public String getAuthorities_contacted() {
			return this.authorities_contacted;
		}

		public String incident_state;

		public String getIncident_state() {
			return this.incident_state;
		}

		public String incident_city;

		public String getIncident_city() {
			return this.incident_city;
		}

		public String incident_location;

		public String getIncident_location() {
			return this.incident_location;
		}

		public Integer incident_hour_of_the_day;

		public Integer getIncident_hour_of_the_day() {
			return this.incident_hour_of_the_day;
		}

		public Integer number_of_vehicles_involved;

		public Integer getNumber_of_vehicles_involved() {
			return this.number_of_vehicles_involved;
		}

		public String property_damage;

		public String getProperty_damage() {
			return this.property_damage;
		}

		public Integer bodily_injuries;

		public Integer getBodily_injuries() {
			return this.bodily_injuries;
		}

		public Integer witnesses;

		public Integer getWitnesses() {
			return this.witnesses;
		}

		public String police_report_available;

		public String getPolice_report_available() {
			return this.police_report_available;
		}

		public Integer total_claim_amount;

		public Integer getTotal_claim_amount() {
			return this.total_claim_amount;
		}

		public Integer injury_claim;

		public Integer getInjury_claim() {
			return this.injury_claim;
		}

		public Integer property_claim;

		public Integer getProperty_claim() {
			return this.property_claim;
		}

		public Integer vehicle_claim;

		public Integer getVehicle_claim() {
			return this.vehicle_claim;
		}

		public String auto_make;

		public String getAuto_make() {
			return this.auto_make;
		}

		public String auto_model;

		public String getAuto_model() {
			return this.auto_model;
		}

		public Integer auto_year;

		public Integer getAuto_year() {
			return this.auto_year;
		}

		public String fraud_reported;

		public String getFraud_reported() {
			return this.fraud_reported;
		}

		public int id;

		public int getId() {
			return this.id;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.id;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row1Struct other = (row1Struct) obj;

			if (this.id != other.id)
				return false;

			return true;
		}

		public void copyDataTo(row1Struct other) {

			other.months_as_customer = this.months_as_customer;
			other.age = this.age;
			other.policy_number = this.policy_number;
			other.policy_bind_date = this.policy_bind_date;
			other.policy_state = this.policy_state;
			other.policy_csl = this.policy_csl;
			other.policy_deductable = this.policy_deductable;
			other.policy_annual_premium = this.policy_annual_premium;
			other.umbrella_limit = this.umbrella_limit;
			other.insured_zip = this.insured_zip;
			other.insured_sex = this.insured_sex;
			other.insured_education_level = this.insured_education_level;
			other.insured_occupation = this.insured_occupation;
			other.insured_hobbies = this.insured_hobbies;
			other.insured_relationship = this.insured_relationship;
			other.capital_gains = this.capital_gains;
			other.capital_loss = this.capital_loss;
			other.incident_date = this.incident_date;
			other.incident_type = this.incident_type;
			other.collision_type = this.collision_type;
			other.incident_severity = this.incident_severity;
			other.authorities_contacted = this.authorities_contacted;
			other.incident_state = this.incident_state;
			other.incident_city = this.incident_city;
			other.incident_location = this.incident_location;
			other.incident_hour_of_the_day = this.incident_hour_of_the_day;
			other.number_of_vehicles_involved = this.number_of_vehicles_involved;
			other.property_damage = this.property_damage;
			other.bodily_injuries = this.bodily_injuries;
			other.witnesses = this.witnesses;
			other.police_report_available = this.police_report_available;
			other.total_claim_amount = this.total_claim_amount;
			other.injury_claim = this.injury_claim;
			other.property_claim = this.property_claim;
			other.vehicle_claim = this.vehicle_claim;
			other.auto_make = this.auto_make;
			other.auto_model = this.auto_model;
			other.auto_year = this.auto_year;
			other.fraud_reported = this.fraud_reported;
			other.id = this.id;

		}

		public void copyKeysDataTo(row1Struct other) {

			other.id = this.id;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db.length) {
					if (length < 1024 && commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db.length == 0) {
						commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db = new byte[1024];
					} else {
						commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db, 0, length);
				strReturn = new String(commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db, 0, length,
						utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db.length) {
					if (length < 1024 && commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db.length == 0) {
						commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db = new byte[1024];
					} else {
						commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db, 0, length);
				strReturn = new String(commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db, 0, length,
						utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db) {

				try {

					int length = 0;

					this.months_as_customer = readInteger(dis);

					this.age = readInteger(dis);

					this.policy_number = readInteger(dis);

					this.policy_bind_date = readDate(dis);

					this.policy_state = readString(dis);

					this.policy_csl = readString(dis);

					this.policy_deductable = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.policy_annual_premium = null;
					} else {
						this.policy_annual_premium = dis.readFloat();
					}

					this.umbrella_limit = readInteger(dis);

					this.insured_zip = readInteger(dis);

					this.insured_sex = readString(dis);

					this.insured_education_level = readString(dis);

					this.insured_occupation = readString(dis);

					this.insured_hobbies = readString(dis);

					this.insured_relationship = readString(dis);

					this.capital_gains = readInteger(dis);

					this.capital_loss = readInteger(dis);

					this.incident_date = readDate(dis);

					this.incident_type = readString(dis);

					this.collision_type = readString(dis);

					this.incident_severity = readString(dis);

					this.authorities_contacted = readString(dis);

					this.incident_state = readString(dis);

					this.incident_city = readString(dis);

					this.incident_location = readString(dis);

					this.incident_hour_of_the_day = readInteger(dis);

					this.number_of_vehicles_involved = readInteger(dis);

					this.property_damage = readString(dis);

					this.bodily_injuries = readInteger(dis);

					this.witnesses = readInteger(dis);

					this.police_report_available = readString(dis);

					this.total_claim_amount = readInteger(dis);

					this.injury_claim = readInteger(dis);

					this.property_claim = readInteger(dis);

					this.vehicle_claim = readInteger(dis);

					this.auto_make = readString(dis);

					this.auto_model = readString(dis);

					this.auto_year = readInteger(dis);

					this.fraud_reported = readString(dis);

					this.id = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db) {

				try {

					int length = 0;

					this.months_as_customer = readInteger(dis);

					this.age = readInteger(dis);

					this.policy_number = readInteger(dis);

					this.policy_bind_date = readDate(dis);

					this.policy_state = readString(dis);

					this.policy_csl = readString(dis);

					this.policy_deductable = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.policy_annual_premium = null;
					} else {
						this.policy_annual_premium = dis.readFloat();
					}

					this.umbrella_limit = readInteger(dis);

					this.insured_zip = readInteger(dis);

					this.insured_sex = readString(dis);

					this.insured_education_level = readString(dis);

					this.insured_occupation = readString(dis);

					this.insured_hobbies = readString(dis);

					this.insured_relationship = readString(dis);

					this.capital_gains = readInteger(dis);

					this.capital_loss = readInteger(dis);

					this.incident_date = readDate(dis);

					this.incident_type = readString(dis);

					this.collision_type = readString(dis);

					this.incident_severity = readString(dis);

					this.authorities_contacted = readString(dis);

					this.incident_state = readString(dis);

					this.incident_city = readString(dis);

					this.incident_location = readString(dis);

					this.incident_hour_of_the_day = readInteger(dis);

					this.number_of_vehicles_involved = readInteger(dis);

					this.property_damage = readString(dis);

					this.bodily_injuries = readInteger(dis);

					this.witnesses = readInteger(dis);

					this.police_report_available = readString(dis);

					this.total_claim_amount = readInteger(dis);

					this.injury_claim = readInteger(dis);

					this.property_claim = readInteger(dis);

					this.vehicle_claim = readInteger(dis);

					this.auto_make = readString(dis);

					this.auto_model = readString(dis);

					this.auto_year = readInteger(dis);

					this.fraud_reported = readString(dis);

					this.id = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.months_as_customer, dos);

				// Integer

				writeInteger(this.age, dos);

				// Integer

				writeInteger(this.policy_number, dos);

				// java.util.Date

				writeDate(this.policy_bind_date, dos);

				// String

				writeString(this.policy_state, dos);

				// String

				writeString(this.policy_csl, dos);

				// Integer

				writeInteger(this.policy_deductable, dos);

				// Float

				if (this.policy_annual_premium == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.policy_annual_premium);
				}

				// Integer

				writeInteger(this.umbrella_limit, dos);

				// Integer

				writeInteger(this.insured_zip, dos);

				// String

				writeString(this.insured_sex, dos);

				// String

				writeString(this.insured_education_level, dos);

				// String

				writeString(this.insured_occupation, dos);

				// String

				writeString(this.insured_hobbies, dos);

				// String

				writeString(this.insured_relationship, dos);

				// Integer

				writeInteger(this.capital_gains, dos);

				// Integer

				writeInteger(this.capital_loss, dos);

				// java.util.Date

				writeDate(this.incident_date, dos);

				// String

				writeString(this.incident_type, dos);

				// String

				writeString(this.collision_type, dos);

				// String

				writeString(this.incident_severity, dos);

				// String

				writeString(this.authorities_contacted, dos);

				// String

				writeString(this.incident_state, dos);

				// String

				writeString(this.incident_city, dos);

				// String

				writeString(this.incident_location, dos);

				// Integer

				writeInteger(this.incident_hour_of_the_day, dos);

				// Integer

				writeInteger(this.number_of_vehicles_involved, dos);

				// String

				writeString(this.property_damage, dos);

				// Integer

				writeInteger(this.bodily_injuries, dos);

				// Integer

				writeInteger(this.witnesses, dos);

				// String

				writeString(this.police_report_available, dos);

				// Integer

				writeInteger(this.total_claim_amount, dos);

				// Integer

				writeInteger(this.injury_claim, dos);

				// Integer

				writeInteger(this.property_claim, dos);

				// Integer

				writeInteger(this.vehicle_claim, dos);

				// String

				writeString(this.auto_make, dos);

				// String

				writeString(this.auto_model, dos);

				// Integer

				writeInteger(this.auto_year, dos);

				// String

				writeString(this.fraud_reported, dos);

				// int

				dos.writeInt(this.id);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.months_as_customer, dos);

				// Integer

				writeInteger(this.age, dos);

				// Integer

				writeInteger(this.policy_number, dos);

				// java.util.Date

				writeDate(this.policy_bind_date, dos);

				// String

				writeString(this.policy_state, dos);

				// String

				writeString(this.policy_csl, dos);

				// Integer

				writeInteger(this.policy_deductable, dos);

				// Float

				if (this.policy_annual_premium == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.policy_annual_premium);
				}

				// Integer

				writeInteger(this.umbrella_limit, dos);

				// Integer

				writeInteger(this.insured_zip, dos);

				// String

				writeString(this.insured_sex, dos);

				// String

				writeString(this.insured_education_level, dos);

				// String

				writeString(this.insured_occupation, dos);

				// String

				writeString(this.insured_hobbies, dos);

				// String

				writeString(this.insured_relationship, dos);

				// Integer

				writeInteger(this.capital_gains, dos);

				// Integer

				writeInteger(this.capital_loss, dos);

				// java.util.Date

				writeDate(this.incident_date, dos);

				// String

				writeString(this.incident_type, dos);

				// String

				writeString(this.collision_type, dos);

				// String

				writeString(this.incident_severity, dos);

				// String

				writeString(this.authorities_contacted, dos);

				// String

				writeString(this.incident_state, dos);

				// String

				writeString(this.incident_city, dos);

				// String

				writeString(this.incident_location, dos);

				// Integer

				writeInteger(this.incident_hour_of_the_day, dos);

				// Integer

				writeInteger(this.number_of_vehicles_involved, dos);

				// String

				writeString(this.property_damage, dos);

				// Integer

				writeInteger(this.bodily_injuries, dos);

				// Integer

				writeInteger(this.witnesses, dos);

				// String

				writeString(this.police_report_available, dos);

				// Integer

				writeInteger(this.total_claim_amount, dos);

				// Integer

				writeInteger(this.injury_claim, dos);

				// Integer

				writeInteger(this.property_claim, dos);

				// Integer

				writeInteger(this.vehicle_claim, dos);

				// String

				writeString(this.auto_make, dos);

				// String

				writeString(this.auto_model, dos);

				// Integer

				writeInteger(this.auto_year, dos);

				// String

				writeString(this.fraud_reported, dos);

				// int

				dos.writeInt(this.id);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("months_as_customer=" + String.valueOf(months_as_customer));
			sb.append(",age=" + String.valueOf(age));
			sb.append(",policy_number=" + String.valueOf(policy_number));
			sb.append(",policy_bind_date=" + String.valueOf(policy_bind_date));
			sb.append(",policy_state=" + policy_state);
			sb.append(",policy_csl=" + policy_csl);
			sb.append(",policy_deductable=" + String.valueOf(policy_deductable));
			sb.append(",policy_annual_premium=" + String.valueOf(policy_annual_premium));
			sb.append(",umbrella_limit=" + String.valueOf(umbrella_limit));
			sb.append(",insured_zip=" + String.valueOf(insured_zip));
			sb.append(",insured_sex=" + insured_sex);
			sb.append(",insured_education_level=" + insured_education_level);
			sb.append(",insured_occupation=" + insured_occupation);
			sb.append(",insured_hobbies=" + insured_hobbies);
			sb.append(",insured_relationship=" + insured_relationship);
			sb.append(",capital_gains=" + String.valueOf(capital_gains));
			sb.append(",capital_loss=" + String.valueOf(capital_loss));
			sb.append(",incident_date=" + String.valueOf(incident_date));
			sb.append(",incident_type=" + incident_type);
			sb.append(",collision_type=" + collision_type);
			sb.append(",incident_severity=" + incident_severity);
			sb.append(",authorities_contacted=" + authorities_contacted);
			sb.append(",incident_state=" + incident_state);
			sb.append(",incident_city=" + incident_city);
			sb.append(",incident_location=" + incident_location);
			sb.append(",incident_hour_of_the_day=" + String.valueOf(incident_hour_of_the_day));
			sb.append(",number_of_vehicles_involved=" + String.valueOf(number_of_vehicles_involved));
			sb.append(",property_damage=" + property_damage);
			sb.append(",bodily_injuries=" + String.valueOf(bodily_injuries));
			sb.append(",witnesses=" + String.valueOf(witnesses));
			sb.append(",police_report_available=" + police_report_available);
			sb.append(",total_claim_amount=" + String.valueOf(total_claim_amount));
			sb.append(",injury_claim=" + String.valueOf(injury_claim));
			sb.append(",property_claim=" + String.valueOf(property_claim));
			sb.append(",vehicle_claim=" + String.valueOf(vehicle_claim));
			sb.append(",auto_make=" + auto_make);
			sb.append(",auto_model=" + auto_model);
			sb.append(",auto_year=" + String.valueOf(auto_year));
			sb.append(",fraud_reported=" + fraud_reported);
			sb.append(",id=" + String.valueOf(id));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row1Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.id, other.id);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row1Struct row1 = new row1Struct();

				/**
				 * [tFileOutputMSDelimited_1 begin ] start
				 */

				ok_Hash.put("tFileOutputMSDelimited_1", false);
				start_Hash.put("tFileOutputMSDelimited_1", System.currentTimeMillis());

				currentComponent = "tFileOutputMSDelimited_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row1");
				}

				int tos_count_tFileOutputMSDelimited_1 = 0;

				int nb_line_tFileOutputMSDelimited_1 = 0;
				String fileName_tFileOutputMSDelimited_1 = "B:/studio/workspace/out.csv";
				java.io.File createFiletFileOutputMSDelimited_1 = new java.io.File(fileName_tFileOutputMSDelimited_1);
//create directory only if not exists
				java.io.File parentFile_tFileOutputMSDelimited_1 = createFiletFileOutputMSDelimited_1.getParentFile();
				if (parentFile_tFileOutputMSDelimited_1 != null && !parentFile_tFileOutputMSDelimited_1.exists()) {
					parentFile_tFileOutputMSDelimited_1.mkdirs();
				}

				final String OUT_DELIM_ROWSEP_tFileOutputMSDelimited_1 = /**
																			 * Start field
																			 * tFileOutputMSDelimited_1:ROWSEPARATOR
																			 */
						"\n"/** End field tFileOutputMSDelimited_1:ROWSEPARATOR */
				;

				java.io.Writer outtFileOutputMSDelimited_1 = new java.io.BufferedWriter(new java.io.OutputStreamWriter(
						new java.io.FileOutputStream(fileName_tFileOutputMSDelimited_1, false), "UTF-8"));

				java.util.List<row1Struct> row1List_tFileOutputMSDelimited_1 = new java.util.ArrayList<row1Struct>();

				/**
				 * [tFileOutputMSDelimited_1 begin ] stop
				 */

				/**
				 * [tDBInput_1 begin ] start
				 */

				ok_Hash.put("tDBInput_1", false);
				start_Hash.put("tDBInput_1", System.currentTimeMillis());

				currentComponent = "tDBInput_1";

				int tos_count_tDBInput_1 = 0;

				java.util.Calendar calendar_tDBInput_1 = java.util.Calendar.getInstance();
				calendar_tDBInput_1.set(0, 0, 0, 0, 0, 0);
				java.util.Date year0_tDBInput_1 = calendar_tDBInput_1.getTime();
				int nb_line_tDBInput_1 = 0;
				java.sql.Connection conn_tDBInput_1 = null;
				String driverClass_tDBInput_1 = "com.mysql.cj.jdbc.Driver";
				java.lang.Class jdbcclazz_tDBInput_1 = java.lang.Class.forName(driverClass_tDBInput_1);
				String dbUser_tDBInput_1 = "springuser";

				final String decryptedPassword_tDBInput_1 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:mKjtziacNfbX2ZqaoiUfiAEL+URJ8mPD0QOArFohGkWEstf+cRY=");

				String dbPwd_tDBInput_1 = decryptedPassword_tDBInput_1;

				String properties_tDBInput_1 = "serverTimezone=UTC";
				if (properties_tDBInput_1 == null || properties_tDBInput_1.trim().length() == 0) {
					properties_tDBInput_1 = "";
				}
				String url_tDBInput_1 = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "inssurance" + "?"
						+ properties_tDBInput_1;

				conn_tDBInput_1 = java.sql.DriverManager.getConnection(url_tDBInput_1, dbUser_tDBInput_1,
						dbPwd_tDBInput_1);

				java.sql.Statement stmt_tDBInput_1 = conn_tDBInput_1.createStatement();

				String dbquery_tDBInput_1 = "SELECT \n  `datawarehouse`.`months_as_customer`, \n  `datawarehouse`.`age`, \n  `datawarehouse`.`policy_number`, \n  `dataw"
						+ "arehouse`.`policy_bind_date`, \n  `datawarehouse`.`policy_state`, \n  `datawarehouse`.`policy_csl`, \n  `datawarehouse`.`po"
						+ "licy_deductable`, \n  `datawarehouse`.`policy_annual_premium`, \n  `datawarehouse`.`umbrella_limit`, \n  `datawarehouse`.`i"
						+ "nsured_zip`, \n  `datawarehouse`.`insured_sex`, \n  `datawarehouse`.`insured_education_level`, \n  `datawarehouse`.`insured"
						+ "_occupation`, \n  `datawarehouse`.`insured_hobbies`, \n  `datawarehouse`.`insured_relationship`, \n  `datawarehouse`.`capit"
						+ "al_gains`, \n  `datawarehouse`.`capital_loss`, \n  `datawarehouse`.`incident_date`, \n  `datawarehouse`.`incident_type`, \n "
						+ " `datawarehouse`.`collision_type`, \n  `datawarehouse`.`incident_severity`, \n  `datawarehouse`.`authorities_contacted`, \n"
						+ "  `datawarehouse`.`incident_state`, \n  `datawarehouse`.`incident_city`, \n  `datawarehouse`.`incident_location`, \n  `data"
						+ "warehouse`.`incident_hour_of_the_day`, \n  `datawarehouse`.`number_of_vehicles_involved`, \n  `datawarehouse`.`property_da"
						+ "mage`, \n  `datawarehouse`.`bodily_injuries`, \n  `datawarehouse`.`witnesses`, \n  `datawarehouse`.`police_report_available"
						+ "`, \n  `datawarehouse`.`total_claim_amount`, \n  `datawarehouse`.`injury_claim`, \n  `datawarehouse`.`property_claim`, \n  `"
						+ "datawarehouse`.`vehicle_claim`, \n  `datawarehouse`.`auto_make`, \n  `datawarehouse`.`auto_model`, \n  `datawarehouse`.`aut"
						+ "o_year`, \n  `datawarehouse`.`fraud_reported`, \n  `datawarehouse`.`id`\nFROM `datawarehouse`";

				globalMap.put("tDBInput_1_QUERY", dbquery_tDBInput_1);
				java.sql.ResultSet rs_tDBInput_1 = null;

				try {
					rs_tDBInput_1 = stmt_tDBInput_1.executeQuery(dbquery_tDBInput_1);
					java.sql.ResultSetMetaData rsmd_tDBInput_1 = rs_tDBInput_1.getMetaData();
					int colQtyInRs_tDBInput_1 = rsmd_tDBInput_1.getColumnCount();

					String tmpContent_tDBInput_1 = null;

					while (rs_tDBInput_1.next()) {
						nb_line_tDBInput_1++;

						if (colQtyInRs_tDBInput_1 < 1) {
							row1.months_as_customer = null;
						} else {

							row1.months_as_customer = rs_tDBInput_1.getInt(1);
							if (rs_tDBInput_1.wasNull()) {
								row1.months_as_customer = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 2) {
							row1.age = null;
						} else {

							row1.age = rs_tDBInput_1.getInt(2);
							if (rs_tDBInput_1.wasNull()) {
								row1.age = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 3) {
							row1.policy_number = null;
						} else {

							row1.policy_number = rs_tDBInput_1.getInt(3);
							if (rs_tDBInput_1.wasNull()) {
								row1.policy_number = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 4) {
							row1.policy_bind_date = null;
						} else {

							if (rs_tDBInput_1.getString(4) != null) {
								String dateString_tDBInput_1 = rs_tDBInput_1.getString(4);
								if (!("0000-00-00").equals(dateString_tDBInput_1)
										&& !("0000-00-00 00:00:00").equals(dateString_tDBInput_1)) {
									row1.policy_bind_date = rs_tDBInput_1.getTimestamp(4);
								} else {
									row1.policy_bind_date = (java.util.Date) year0_tDBInput_1.clone();
								}
							} else {
								row1.policy_bind_date = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 5) {
							row1.policy_state = null;
						} else {

							row1.policy_state = routines.system.JDBCUtil.getString(rs_tDBInput_1, 5, false);
						}
						if (colQtyInRs_tDBInput_1 < 6) {
							row1.policy_csl = null;
						} else {

							row1.policy_csl = routines.system.JDBCUtil.getString(rs_tDBInput_1, 6, false);
						}
						if (colQtyInRs_tDBInput_1 < 7) {
							row1.policy_deductable = null;
						} else {

							row1.policy_deductable = rs_tDBInput_1.getInt(7);
							if (rs_tDBInput_1.wasNull()) {
								row1.policy_deductable = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 8) {
							row1.policy_annual_premium = null;
						} else {

							row1.policy_annual_premium = rs_tDBInput_1.getFloat(8);
							if (rs_tDBInput_1.wasNull()) {
								row1.policy_annual_premium = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 9) {
							row1.umbrella_limit = null;
						} else {

							row1.umbrella_limit = rs_tDBInput_1.getInt(9);
							if (rs_tDBInput_1.wasNull()) {
								row1.umbrella_limit = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 10) {
							row1.insured_zip = null;
						} else {

							row1.insured_zip = rs_tDBInput_1.getInt(10);
							if (rs_tDBInput_1.wasNull()) {
								row1.insured_zip = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 11) {
							row1.insured_sex = null;
						} else {

							row1.insured_sex = routines.system.JDBCUtil.getString(rs_tDBInput_1, 11, false);
						}
						if (colQtyInRs_tDBInput_1 < 12) {
							row1.insured_education_level = null;
						} else {

							row1.insured_education_level = routines.system.JDBCUtil.getString(rs_tDBInput_1, 12, false);
						}
						if (colQtyInRs_tDBInput_1 < 13) {
							row1.insured_occupation = null;
						} else {

							row1.insured_occupation = routines.system.JDBCUtil.getString(rs_tDBInput_1, 13, false);
						}
						if (colQtyInRs_tDBInput_1 < 14) {
							row1.insured_hobbies = null;
						} else {

							row1.insured_hobbies = routines.system.JDBCUtil.getString(rs_tDBInput_1, 14, false);
						}
						if (colQtyInRs_tDBInput_1 < 15) {
							row1.insured_relationship = null;
						} else {

							row1.insured_relationship = routines.system.JDBCUtil.getString(rs_tDBInput_1, 15, false);
						}
						if (colQtyInRs_tDBInput_1 < 16) {
							row1.capital_gains = null;
						} else {

							row1.capital_gains = rs_tDBInput_1.getInt(16);
							if (rs_tDBInput_1.wasNull()) {
								row1.capital_gains = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 17) {
							row1.capital_loss = null;
						} else {

							row1.capital_loss = rs_tDBInput_1.getInt(17);
							if (rs_tDBInput_1.wasNull()) {
								row1.capital_loss = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 18) {
							row1.incident_date = null;
						} else {

							if (rs_tDBInput_1.getString(18) != null) {
								String dateString_tDBInput_1 = rs_tDBInput_1.getString(18);
								if (!("0000-00-00").equals(dateString_tDBInput_1)
										&& !("0000-00-00 00:00:00").equals(dateString_tDBInput_1)) {
									row1.incident_date = rs_tDBInput_1.getTimestamp(18);
								} else {
									row1.incident_date = (java.util.Date) year0_tDBInput_1.clone();
								}
							} else {
								row1.incident_date = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 19) {
							row1.incident_type = null;
						} else {

							row1.incident_type = routines.system.JDBCUtil.getString(rs_tDBInput_1, 19, false);
						}
						if (colQtyInRs_tDBInput_1 < 20) {
							row1.collision_type = null;
						} else {

							row1.collision_type = routines.system.JDBCUtil.getString(rs_tDBInput_1, 20, false);
						}
						if (colQtyInRs_tDBInput_1 < 21) {
							row1.incident_severity = null;
						} else {

							row1.incident_severity = routines.system.JDBCUtil.getString(rs_tDBInput_1, 21, false);
						}
						if (colQtyInRs_tDBInput_1 < 22) {
							row1.authorities_contacted = null;
						} else {

							row1.authorities_contacted = routines.system.JDBCUtil.getString(rs_tDBInput_1, 22, false);
						}
						if (colQtyInRs_tDBInput_1 < 23) {
							row1.incident_state = null;
						} else {

							row1.incident_state = routines.system.JDBCUtil.getString(rs_tDBInput_1, 23, false);
						}
						if (colQtyInRs_tDBInput_1 < 24) {
							row1.incident_city = null;
						} else {

							row1.incident_city = routines.system.JDBCUtil.getString(rs_tDBInput_1, 24, false);
						}
						if (colQtyInRs_tDBInput_1 < 25) {
							row1.incident_location = null;
						} else {

							row1.incident_location = routines.system.JDBCUtil.getString(rs_tDBInput_1, 25, false);
						}
						if (colQtyInRs_tDBInput_1 < 26) {
							row1.incident_hour_of_the_day = null;
						} else {

							row1.incident_hour_of_the_day = rs_tDBInput_1.getInt(26);
							if (rs_tDBInput_1.wasNull()) {
								row1.incident_hour_of_the_day = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 27) {
							row1.number_of_vehicles_involved = null;
						} else {

							row1.number_of_vehicles_involved = rs_tDBInput_1.getInt(27);
							if (rs_tDBInput_1.wasNull()) {
								row1.number_of_vehicles_involved = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 28) {
							row1.property_damage = null;
						} else {

							row1.property_damage = routines.system.JDBCUtil.getString(rs_tDBInput_1, 28, false);
						}
						if (colQtyInRs_tDBInput_1 < 29) {
							row1.bodily_injuries = null;
						} else {

							row1.bodily_injuries = rs_tDBInput_1.getInt(29);
							if (rs_tDBInput_1.wasNull()) {
								row1.bodily_injuries = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 30) {
							row1.witnesses = null;
						} else {

							row1.witnesses = rs_tDBInput_1.getInt(30);
							if (rs_tDBInput_1.wasNull()) {
								row1.witnesses = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 31) {
							row1.police_report_available = null;
						} else {

							row1.police_report_available = routines.system.JDBCUtil.getString(rs_tDBInput_1, 31, false);
						}
						if (colQtyInRs_tDBInput_1 < 32) {
							row1.total_claim_amount = null;
						} else {

							row1.total_claim_amount = rs_tDBInput_1.getInt(32);
							if (rs_tDBInput_1.wasNull()) {
								row1.total_claim_amount = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 33) {
							row1.injury_claim = null;
						} else {

							row1.injury_claim = rs_tDBInput_1.getInt(33);
							if (rs_tDBInput_1.wasNull()) {
								row1.injury_claim = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 34) {
							row1.property_claim = null;
						} else {

							row1.property_claim = rs_tDBInput_1.getInt(34);
							if (rs_tDBInput_1.wasNull()) {
								row1.property_claim = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 35) {
							row1.vehicle_claim = null;
						} else {

							row1.vehicle_claim = rs_tDBInput_1.getInt(35);
							if (rs_tDBInput_1.wasNull()) {
								row1.vehicle_claim = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 36) {
							row1.auto_make = null;
						} else {

							row1.auto_make = routines.system.JDBCUtil.getString(rs_tDBInput_1, 36, false);
						}
						if (colQtyInRs_tDBInput_1 < 37) {
							row1.auto_model = null;
						} else {

							row1.auto_model = routines.system.JDBCUtil.getString(rs_tDBInput_1, 37, false);
						}
						if (colQtyInRs_tDBInput_1 < 38) {
							row1.auto_year = null;
						} else {

							row1.auto_year = rs_tDBInput_1.getInt(38);
							if (rs_tDBInput_1.wasNull()) {
								row1.auto_year = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 39) {
							row1.fraud_reported = null;
						} else {

							row1.fraud_reported = routines.system.JDBCUtil.getString(rs_tDBInput_1, 39, false);
						}
						if (colQtyInRs_tDBInput_1 < 40) {
							row1.id = 0;
						} else {

							row1.id = rs_tDBInput_1.getInt(40);
							if (rs_tDBInput_1.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}

						/**
						 * [tDBInput_1 begin ] stop
						 */

						/**
						 * [tDBInput_1 main ] start
						 */

						currentComponent = "tDBInput_1";

						tos_count_tDBInput_1++;

						/**
						 * [tDBInput_1 main ] stop
						 */

						/**
						 * [tDBInput_1 process_data_begin ] start
						 */

						currentComponent = "tDBInput_1";

						/**
						 * [tDBInput_1 process_data_begin ] stop
						 */

						/**
						 * [tFileOutputMSDelimited_1 main ] start
						 */

						currentComponent = "tFileOutputMSDelimited_1";

						if (execStat) {
							runStat.updateStatOnConnection(iterateId, 1, 1

									, "row1"

							);
						}

						row1Struct row1_tFileOutputMSDelimited_1 = new row1Struct();
						row1_tFileOutputMSDelimited_1.months_as_customer = row1.months_as_customer;
						row1_tFileOutputMSDelimited_1.age = row1.age;
						row1_tFileOutputMSDelimited_1.policy_number = row1.policy_number;
						row1_tFileOutputMSDelimited_1.policy_bind_date = row1.policy_bind_date;
						row1_tFileOutputMSDelimited_1.policy_state = row1.policy_state;
						row1_tFileOutputMSDelimited_1.policy_csl = row1.policy_csl;
						row1_tFileOutputMSDelimited_1.policy_deductable = row1.policy_deductable;
						row1_tFileOutputMSDelimited_1.policy_annual_premium = row1.policy_annual_premium;
						row1_tFileOutputMSDelimited_1.umbrella_limit = row1.umbrella_limit;
						row1_tFileOutputMSDelimited_1.insured_zip = row1.insured_zip;
						row1_tFileOutputMSDelimited_1.insured_sex = row1.insured_sex;
						row1_tFileOutputMSDelimited_1.insured_education_level = row1.insured_education_level;
						row1_tFileOutputMSDelimited_1.insured_occupation = row1.insured_occupation;
						row1_tFileOutputMSDelimited_1.insured_hobbies = row1.insured_hobbies;
						row1_tFileOutputMSDelimited_1.insured_relationship = row1.insured_relationship;
						row1_tFileOutputMSDelimited_1.capital_gains = row1.capital_gains;
						row1_tFileOutputMSDelimited_1.capital_loss = row1.capital_loss;
						row1_tFileOutputMSDelimited_1.incident_date = row1.incident_date;
						row1_tFileOutputMSDelimited_1.incident_type = row1.incident_type;
						row1_tFileOutputMSDelimited_1.collision_type = row1.collision_type;
						row1_tFileOutputMSDelimited_1.incident_severity = row1.incident_severity;
						row1_tFileOutputMSDelimited_1.authorities_contacted = row1.authorities_contacted;
						row1_tFileOutputMSDelimited_1.incident_state = row1.incident_state;
						row1_tFileOutputMSDelimited_1.incident_city = row1.incident_city;
						row1_tFileOutputMSDelimited_1.incident_location = row1.incident_location;
						row1_tFileOutputMSDelimited_1.incident_hour_of_the_day = row1.incident_hour_of_the_day;
						row1_tFileOutputMSDelimited_1.number_of_vehicles_involved = row1.number_of_vehicles_involved;
						row1_tFileOutputMSDelimited_1.property_damage = row1.property_damage;
						row1_tFileOutputMSDelimited_1.bodily_injuries = row1.bodily_injuries;
						row1_tFileOutputMSDelimited_1.witnesses = row1.witnesses;
						row1_tFileOutputMSDelimited_1.police_report_available = row1.police_report_available;
						row1_tFileOutputMSDelimited_1.total_claim_amount = row1.total_claim_amount;
						row1_tFileOutputMSDelimited_1.injury_claim = row1.injury_claim;
						row1_tFileOutputMSDelimited_1.property_claim = row1.property_claim;
						row1_tFileOutputMSDelimited_1.vehicle_claim = row1.vehicle_claim;
						row1_tFileOutputMSDelimited_1.auto_make = row1.auto_make;
						row1_tFileOutputMSDelimited_1.auto_model = row1.auto_model;
						row1_tFileOutputMSDelimited_1.auto_year = row1.auto_year;
						row1_tFileOutputMSDelimited_1.fraud_reported = row1.fraud_reported;
						row1_tFileOutputMSDelimited_1.id = row1.id;
						row1List_tFileOutputMSDelimited_1.add(row1_tFileOutputMSDelimited_1);
						nb_line_tFileOutputMSDelimited_1++;

						tos_count_tFileOutputMSDelimited_1++;

						/**
						 * [tFileOutputMSDelimited_1 main ] stop
						 */

						/**
						 * [tFileOutputMSDelimited_1 process_data_begin ] start
						 */

						currentComponent = "tFileOutputMSDelimited_1";

						/**
						 * [tFileOutputMSDelimited_1 process_data_begin ] stop
						 */

						/**
						 * [tFileOutputMSDelimited_1 process_data_end ] start
						 */

						currentComponent = "tFileOutputMSDelimited_1";

						/**
						 * [tFileOutputMSDelimited_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 process_data_end ] start
						 */

						currentComponent = "tDBInput_1";

						/**
						 * [tDBInput_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 end ] start
						 */

						currentComponent = "tDBInput_1";

					}
				} finally {
					if (rs_tDBInput_1 != null) {
						rs_tDBInput_1.close();
					}
					if (stmt_tDBInput_1 != null) {
						stmt_tDBInput_1.close();
					}
					if (conn_tDBInput_1 != null && !conn_tDBInput_1.isClosed()) {

						conn_tDBInput_1.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

					}

				}

				globalMap.put("tDBInput_1_NB_LINE", nb_line_tDBInput_1);

				ok_Hash.put("tDBInput_1", true);
				end_Hash.put("tDBInput_1", System.currentTimeMillis());

				/**
				 * [tDBInput_1 end ] stop
				 */

				/**
				 * [tFileOutputMSDelimited_1 end ] start
				 */

				currentComponent = "tFileOutputMSDelimited_1";

				String tempStringMtFileOutputMSDelimited_1 = null;

				int tempLengthMtFileOutputMSDelimited_1 = 0;

				StringBuilder sb_tFileOutputMSDelimited_1 = new StringBuilder();
				for (int row1_index_tFileOutputMSDelimited_1 = 0; row1_index_tFileOutputMSDelimited_1 < row1List_tFileOutputMSDelimited_1
						.size(); row1_index_tFileOutputMSDelimited_1++) {
					row1Struct row1_e = row1List_tFileOutputMSDelimited_1.get(row1_index_tFileOutputMSDelimited_1);
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.months_as_customer == null) ? ""
							: String.valueOf(row1_e.months_as_customer);
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.age == null) ? "" : String.valueOf(row1_e.age);
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.policy_number == null) ? ""
							: String.valueOf(row1_e.policy_number);
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.policy_bind_date == null) ? ""
							: FormatterUtils.format_Date(row1_e.policy_bind_date, "dd-MM-yyyy");
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.policy_state == null) ? "" : row1_e.policy_state;
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.policy_csl == null) ? "" : row1_e.policy_csl;
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.policy_deductable == null) ? ""
							: String.valueOf(row1_e.policy_deductable);
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.policy_annual_premium == null) ? ""
							: String.valueOf(row1_e.policy_annual_premium);
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.umbrella_limit == null) ? ""
							: String.valueOf(row1_e.umbrella_limit);
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.insured_zip == null) ? ""
							: String.valueOf(row1_e.insured_zip);
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.insured_sex == null) ? "" : row1_e.insured_sex;
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.insured_education_level == null) ? ""
							: row1_e.insured_education_level;
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.insured_occupation == null) ? ""
							: row1_e.insured_occupation;
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.insured_hobbies == null) ? ""
							: row1_e.insured_hobbies;
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.insured_relationship == null) ? ""
							: row1_e.insured_relationship;
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.capital_gains == null) ? ""
							: String.valueOf(row1_e.capital_gains);
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.capital_loss == null) ? ""
							: String.valueOf(row1_e.capital_loss);
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.incident_date == null) ? ""
							: FormatterUtils.format_Date(row1_e.incident_date, "dd-MM-yyyy");
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.incident_type == null) ? "" : row1_e.incident_type;
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.collision_type == null) ? "" : row1_e.collision_type;
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.incident_severity == null) ? ""
							: row1_e.incident_severity;
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.authorities_contacted == null) ? ""
							: row1_e.authorities_contacted;
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.incident_state == null) ? "" : row1_e.incident_state;
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.incident_city == null) ? "" : row1_e.incident_city;
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.incident_location == null) ? ""
							: row1_e.incident_location;
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.incident_hour_of_the_day == null) ? ""
							: String.valueOf(row1_e.incident_hour_of_the_day);
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.number_of_vehicles_involved == null) ? ""
							: String.valueOf(row1_e.number_of_vehicles_involved);
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.property_damage == null) ? ""
							: row1_e.property_damage;
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.bodily_injuries == null) ? ""
							: String.valueOf(row1_e.bodily_injuries);
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.witnesses == null) ? ""
							: String.valueOf(row1_e.witnesses);
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.police_report_available == null) ? ""
							: row1_e.police_report_available;
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.total_claim_amount == null) ? ""
							: String.valueOf(row1_e.total_claim_amount);
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.injury_claim == null) ? ""
							: String.valueOf(row1_e.injury_claim);
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.property_claim == null) ? ""
							: String.valueOf(row1_e.property_claim);
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.vehicle_claim == null) ? ""
							: String.valueOf(row1_e.vehicle_claim);
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.auto_make == null) ? "" : row1_e.auto_make;
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.auto_model == null) ? "" : row1_e.auto_model;
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.auto_year == null) ? ""
							: String.valueOf(row1_e.auto_year);
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = (row1_e.fraud_reported == null) ? "" : row1_e.fraud_reported;
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append(",");
//get  and format output String begin
					tempStringMtFileOutputMSDelimited_1 = String.valueOf(row1_e.id);
					sb_tFileOutputMSDelimited_1.append(tempStringMtFileOutputMSDelimited_1);
					sb_tFileOutputMSDelimited_1.append("\n");
					outtFileOutputMSDelimited_1.write(sb_tFileOutputMSDelimited_1.toString());
					sb_tFileOutputMSDelimited_1 = new StringBuilder();
				}

				if (outtFileOutputMSDelimited_1 != null) {
					outtFileOutputMSDelimited_1.close();
				}
				globalMap.put("tFileOutputMSDelimited_1_NB_LINE", nb_line_tFileOutputMSDelimited_1);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row1");
				}

				ok_Hash.put("tFileOutputMSDelimited_1", true);
				end_Hash.put("tFileOutputMSDelimited_1", System.currentTimeMillis());

				/**
				 * [tFileOutputMSDelimited_1 end ] stop
				 */
			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_1 finally ] start
				 */

				currentComponent = "tDBInput_1";

				/**
				 * [tDBInput_1 finally ] stop
				 */

				/**
				 * [tFileOutputMSDelimited_1 finally ] start
				 */

				currentComponent = "tFileOutputMSDelimited_1";

				/**
				 * [tFileOutputMSDelimited_1 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 1);
	}

	public static class out1Struct implements routines.system.IPersistableRow<out1Struct> {
		final static byte[] commonByteArrayLock_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db = new byte[0];
		static byte[] commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db = new byte[0];

		public String collision_type;

		public String getCollision_type() {
			return this.collision_type;
		}

		public String property_damage;

		public String getProperty_damage() {
			return this.property_damage;
		}

		public String police_report_available;

		public String getPolice_report_available() {
			return this.police_report_available;
		}

		public int fraud_reported;

		public int getFraud_reported() {
			return this.fraud_reported;
		}

		public java.util.Date incident_date;

		public java.util.Date getIncident_date() {
			return this.incident_date;
		}

		public java.util.Date policy_bind_date;

		public java.util.Date getPolicy_bind_date() {
			return this.policy_bind_date;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db.length) {
					if (length < 1024 && commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db.length == 0) {
						commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db = new byte[1024];
					} else {
						commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db, 0, length);
				strReturn = new String(commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db, 0, length,
						utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db.length) {
					if (length < 1024 && commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db.length == 0) {
						commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db = new byte[1024];
					} else {
						commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db, 0, length);
				strReturn = new String(commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db, 0, length,
						utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db) {

				try {

					int length = 0;

					this.collision_type = readString(dis);

					this.property_damage = readString(dis);

					this.police_report_available = readString(dis);

					this.fraud_reported = dis.readInt();

					this.incident_date = readDate(dis);

					this.policy_bind_date = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db) {

				try {

					int length = 0;

					this.collision_type = readString(dis);

					this.property_damage = readString(dis);

					this.police_report_available = readString(dis);

					this.fraud_reported = dis.readInt();

					this.incident_date = readDate(dis);

					this.policy_bind_date = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.collision_type, dos);

				// String

				writeString(this.property_damage, dos);

				// String

				writeString(this.police_report_available, dos);

				// int

				dos.writeInt(this.fraud_reported);

				// java.util.Date

				writeDate(this.incident_date, dos);

				// java.util.Date

				writeDate(this.policy_bind_date, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.collision_type, dos);

				// String

				writeString(this.property_damage, dos);

				// String

				writeString(this.police_report_available, dos);

				// int

				dos.writeInt(this.fraud_reported);

				// java.util.Date

				writeDate(this.incident_date, dos);

				// java.util.Date

				writeDate(this.policy_bind_date, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("collision_type=" + collision_type);
			sb.append(",property_damage=" + property_damage);
			sb.append(",police_report_available=" + police_report_available);
			sb.append(",fraud_reported=" + String.valueOf(fraud_reported));
			sb.append(",incident_date=" + String.valueOf(incident_date));
			sb.append(",policy_bind_date=" + String.valueOf(policy_bind_date));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(out1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row2Struct implements routines.system.IPersistableRow<row2Struct> {
		final static byte[] commonByteArrayLock_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db = new byte[0];
		static byte[] commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db = new byte[0];

		public Integer months_as_customer;

		public Integer getMonths_as_customer() {
			return this.months_as_customer;
		}

		public Integer age;

		public Integer getAge() {
			return this.age;
		}

		public Integer policy_number;

		public Integer getPolicy_number() {
			return this.policy_number;
		}

		public java.util.Date policy_bind_date;

		public java.util.Date getPolicy_bind_date() {
			return this.policy_bind_date;
		}

		public String policy_state;

		public String getPolicy_state() {
			return this.policy_state;
		}

		public String policy_csl;

		public String getPolicy_csl() {
			return this.policy_csl;
		}

		public Integer policy_deductable;

		public Integer getPolicy_deductable() {
			return this.policy_deductable;
		}

		public Float policy_annual_premium;

		public Float getPolicy_annual_premium() {
			return this.policy_annual_premium;
		}

		public Integer umbrella_limit;

		public Integer getUmbrella_limit() {
			return this.umbrella_limit;
		}

		public Integer insured_zip;

		public Integer getInsured_zip() {
			return this.insured_zip;
		}

		public String insured_sex;

		public String getInsured_sex() {
			return this.insured_sex;
		}

		public String insured_education_level;

		public String getInsured_education_level() {
			return this.insured_education_level;
		}

		public String insured_occupation;

		public String getInsured_occupation() {
			return this.insured_occupation;
		}

		public String insured_hobbies;

		public String getInsured_hobbies() {
			return this.insured_hobbies;
		}

		public String insured_relationship;

		public String getInsured_relationship() {
			return this.insured_relationship;
		}

		public Integer capital_gains;

		public Integer getCapital_gains() {
			return this.capital_gains;
		}

		public Integer capital_loss;

		public Integer getCapital_loss() {
			return this.capital_loss;
		}

		public java.util.Date incident_date;

		public java.util.Date getIncident_date() {
			return this.incident_date;
		}

		public String incident_type;

		public String getIncident_type() {
			return this.incident_type;
		}

		public String collision_type;

		public String getCollision_type() {
			return this.collision_type;
		}

		public String incident_severity;

		public String getIncident_severity() {
			return this.incident_severity;
		}

		public String authorities_contacted;

		public String getAuthorities_contacted() {
			return this.authorities_contacted;
		}

		public String incident_state;

		public String getIncident_state() {
			return this.incident_state;
		}

		public String incident_city;

		public String getIncident_city() {
			return this.incident_city;
		}

		public String incident_location;

		public String getIncident_location() {
			return this.incident_location;
		}

		public Integer incident_hour_of_the_day;

		public Integer getIncident_hour_of_the_day() {
			return this.incident_hour_of_the_day;
		}

		public Integer number_of_vehicles_involved;

		public Integer getNumber_of_vehicles_involved() {
			return this.number_of_vehicles_involved;
		}

		public String property_damage;

		public String getProperty_damage() {
			return this.property_damage;
		}

		public Integer bodily_injuries;

		public Integer getBodily_injuries() {
			return this.bodily_injuries;
		}

		public Integer witnesses;

		public Integer getWitnesses() {
			return this.witnesses;
		}

		public String police_report_available;

		public String getPolice_report_available() {
			return this.police_report_available;
		}

		public Integer total_claim_amount;

		public Integer getTotal_claim_amount() {
			return this.total_claim_amount;
		}

		public Integer injury_claim;

		public Integer getInjury_claim() {
			return this.injury_claim;
		}

		public Integer property_claim;

		public Integer getProperty_claim() {
			return this.property_claim;
		}

		public Integer vehicle_claim;

		public Integer getVehicle_claim() {
			return this.vehicle_claim;
		}

		public String auto_make;

		public String getAuto_make() {
			return this.auto_make;
		}

		public String auto_model;

		public String getAuto_model() {
			return this.auto_model;
		}

		public Integer auto_year;

		public Integer getAuto_year() {
			return this.auto_year;
		}

		public Character fraud_reported;

		public Character getFraud_reported() {
			return this.fraud_reported;
		}

		public String _c39;

		public String get_c39() {
			return this._c39;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db.length) {
					if (length < 1024 && commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db.length == 0) {
						commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db = new byte[1024];
					} else {
						commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db, 0, length);
				strReturn = new String(commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db, 0, length,
						utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db.length) {
					if (length < 1024 && commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db.length == 0) {
						commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db = new byte[1024];
					} else {
						commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db, 0, length);
				strReturn = new String(commonByteArray_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db, 0, length,
						utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db) {

				try {

					int length = 0;

					this.months_as_customer = readInteger(dis);

					this.age = readInteger(dis);

					this.policy_number = readInteger(dis);

					this.policy_bind_date = readDate(dis);

					this.policy_state = readString(dis);

					this.policy_csl = readString(dis);

					this.policy_deductable = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.policy_annual_premium = null;
					} else {
						this.policy_annual_premium = dis.readFloat();
					}

					this.umbrella_limit = readInteger(dis);

					this.insured_zip = readInteger(dis);

					this.insured_sex = readString(dis);

					this.insured_education_level = readString(dis);

					this.insured_occupation = readString(dis);

					this.insured_hobbies = readString(dis);

					this.insured_relationship = readString(dis);

					this.capital_gains = readInteger(dis);

					this.capital_loss = readInteger(dis);

					this.incident_date = readDate(dis);

					this.incident_type = readString(dis);

					this.collision_type = readString(dis);

					this.incident_severity = readString(dis);

					this.authorities_contacted = readString(dis);

					this.incident_state = readString(dis);

					this.incident_city = readString(dis);

					this.incident_location = readString(dis);

					this.incident_hour_of_the_day = readInteger(dis);

					this.number_of_vehicles_involved = readInteger(dis);

					this.property_damage = readString(dis);

					this.bodily_injuries = readInteger(dis);

					this.witnesses = readInteger(dis);

					this.police_report_available = readString(dis);

					this.total_claim_amount = readInteger(dis);

					this.injury_claim = readInteger(dis);

					this.property_claim = readInteger(dis);

					this.vehicle_claim = readInteger(dis);

					this.auto_make = readString(dis);

					this.auto_model = readString(dis);

					this.auto_year = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.fraud_reported = null;
					} else {
						this.fraud_reported = dis.readChar();
					}

					this._c39 = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_BUSINESSINTELLIGENCEMINIPROJET_csv_to_db) {

				try {

					int length = 0;

					this.months_as_customer = readInteger(dis);

					this.age = readInteger(dis);

					this.policy_number = readInteger(dis);

					this.policy_bind_date = readDate(dis);

					this.policy_state = readString(dis);

					this.policy_csl = readString(dis);

					this.policy_deductable = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.policy_annual_premium = null;
					} else {
						this.policy_annual_premium = dis.readFloat();
					}

					this.umbrella_limit = readInteger(dis);

					this.insured_zip = readInteger(dis);

					this.insured_sex = readString(dis);

					this.insured_education_level = readString(dis);

					this.insured_occupation = readString(dis);

					this.insured_hobbies = readString(dis);

					this.insured_relationship = readString(dis);

					this.capital_gains = readInteger(dis);

					this.capital_loss = readInteger(dis);

					this.incident_date = readDate(dis);

					this.incident_type = readString(dis);

					this.collision_type = readString(dis);

					this.incident_severity = readString(dis);

					this.authorities_contacted = readString(dis);

					this.incident_state = readString(dis);

					this.incident_city = readString(dis);

					this.incident_location = readString(dis);

					this.incident_hour_of_the_day = readInteger(dis);

					this.number_of_vehicles_involved = readInteger(dis);

					this.property_damage = readString(dis);

					this.bodily_injuries = readInteger(dis);

					this.witnesses = readInteger(dis);

					this.police_report_available = readString(dis);

					this.total_claim_amount = readInteger(dis);

					this.injury_claim = readInteger(dis);

					this.property_claim = readInteger(dis);

					this.vehicle_claim = readInteger(dis);

					this.auto_make = readString(dis);

					this.auto_model = readString(dis);

					this.auto_year = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.fraud_reported = null;
					} else {
						this.fraud_reported = dis.readChar();
					}

					this._c39 = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.months_as_customer, dos);

				// Integer

				writeInteger(this.age, dos);

				// Integer

				writeInteger(this.policy_number, dos);

				// java.util.Date

				writeDate(this.policy_bind_date, dos);

				// String

				writeString(this.policy_state, dos);

				// String

				writeString(this.policy_csl, dos);

				// Integer

				writeInteger(this.policy_deductable, dos);

				// Float

				if (this.policy_annual_premium == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.policy_annual_premium);
				}

				// Integer

				writeInteger(this.umbrella_limit, dos);

				// Integer

				writeInteger(this.insured_zip, dos);

				// String

				writeString(this.insured_sex, dos);

				// String

				writeString(this.insured_education_level, dos);

				// String

				writeString(this.insured_occupation, dos);

				// String

				writeString(this.insured_hobbies, dos);

				// String

				writeString(this.insured_relationship, dos);

				// Integer

				writeInteger(this.capital_gains, dos);

				// Integer

				writeInteger(this.capital_loss, dos);

				// java.util.Date

				writeDate(this.incident_date, dos);

				// String

				writeString(this.incident_type, dos);

				// String

				writeString(this.collision_type, dos);

				// String

				writeString(this.incident_severity, dos);

				// String

				writeString(this.authorities_contacted, dos);

				// String

				writeString(this.incident_state, dos);

				// String

				writeString(this.incident_city, dos);

				// String

				writeString(this.incident_location, dos);

				// Integer

				writeInteger(this.incident_hour_of_the_day, dos);

				// Integer

				writeInteger(this.number_of_vehicles_involved, dos);

				// String

				writeString(this.property_damage, dos);

				// Integer

				writeInteger(this.bodily_injuries, dos);

				// Integer

				writeInteger(this.witnesses, dos);

				// String

				writeString(this.police_report_available, dos);

				// Integer

				writeInteger(this.total_claim_amount, dos);

				// Integer

				writeInteger(this.injury_claim, dos);

				// Integer

				writeInteger(this.property_claim, dos);

				// Integer

				writeInteger(this.vehicle_claim, dos);

				// String

				writeString(this.auto_make, dos);

				// String

				writeString(this.auto_model, dos);

				// Integer

				writeInteger(this.auto_year, dos);

				// Character

				if (this.fraud_reported == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeChar(this.fraud_reported);
				}

				// String

				writeString(this._c39, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.months_as_customer, dos);

				// Integer

				writeInteger(this.age, dos);

				// Integer

				writeInteger(this.policy_number, dos);

				// java.util.Date

				writeDate(this.policy_bind_date, dos);

				// String

				writeString(this.policy_state, dos);

				// String

				writeString(this.policy_csl, dos);

				// Integer

				writeInteger(this.policy_deductable, dos);

				// Float

				if (this.policy_annual_premium == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.policy_annual_premium);
				}

				// Integer

				writeInteger(this.umbrella_limit, dos);

				// Integer

				writeInteger(this.insured_zip, dos);

				// String

				writeString(this.insured_sex, dos);

				// String

				writeString(this.insured_education_level, dos);

				// String

				writeString(this.insured_occupation, dos);

				// String

				writeString(this.insured_hobbies, dos);

				// String

				writeString(this.insured_relationship, dos);

				// Integer

				writeInteger(this.capital_gains, dos);

				// Integer

				writeInteger(this.capital_loss, dos);

				// java.util.Date

				writeDate(this.incident_date, dos);

				// String

				writeString(this.incident_type, dos);

				// String

				writeString(this.collision_type, dos);

				// String

				writeString(this.incident_severity, dos);

				// String

				writeString(this.authorities_contacted, dos);

				// String

				writeString(this.incident_state, dos);

				// String

				writeString(this.incident_city, dos);

				// String

				writeString(this.incident_location, dos);

				// Integer

				writeInteger(this.incident_hour_of_the_day, dos);

				// Integer

				writeInteger(this.number_of_vehicles_involved, dos);

				// String

				writeString(this.property_damage, dos);

				// Integer

				writeInteger(this.bodily_injuries, dos);

				// Integer

				writeInteger(this.witnesses, dos);

				// String

				writeString(this.police_report_available, dos);

				// Integer

				writeInteger(this.total_claim_amount, dos);

				// Integer

				writeInteger(this.injury_claim, dos);

				// Integer

				writeInteger(this.property_claim, dos);

				// Integer

				writeInteger(this.vehicle_claim, dos);

				// String

				writeString(this.auto_make, dos);

				// String

				writeString(this.auto_model, dos);

				// Integer

				writeInteger(this.auto_year, dos);

				// Character

				if (this.fraud_reported == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeChar(this.fraud_reported);
				}

				// String

				writeString(this._c39, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("months_as_customer=" + String.valueOf(months_as_customer));
			sb.append(",age=" + String.valueOf(age));
			sb.append(",policy_number=" + String.valueOf(policy_number));
			sb.append(",policy_bind_date=" + String.valueOf(policy_bind_date));
			sb.append(",policy_state=" + policy_state);
			sb.append(",policy_csl=" + policy_csl);
			sb.append(",policy_deductable=" + String.valueOf(policy_deductable));
			sb.append(",policy_annual_premium=" + String.valueOf(policy_annual_premium));
			sb.append(",umbrella_limit=" + String.valueOf(umbrella_limit));
			sb.append(",insured_zip=" + String.valueOf(insured_zip));
			sb.append(",insured_sex=" + insured_sex);
			sb.append(",insured_education_level=" + insured_education_level);
			sb.append(",insured_occupation=" + insured_occupation);
			sb.append(",insured_hobbies=" + insured_hobbies);
			sb.append(",insured_relationship=" + insured_relationship);
			sb.append(",capital_gains=" + String.valueOf(capital_gains));
			sb.append(",capital_loss=" + String.valueOf(capital_loss));
			sb.append(",incident_date=" + String.valueOf(incident_date));
			sb.append(",incident_type=" + incident_type);
			sb.append(",collision_type=" + collision_type);
			sb.append(",incident_severity=" + incident_severity);
			sb.append(",authorities_contacted=" + authorities_contacted);
			sb.append(",incident_state=" + incident_state);
			sb.append(",incident_city=" + incident_city);
			sb.append(",incident_location=" + incident_location);
			sb.append(",incident_hour_of_the_day=" + String.valueOf(incident_hour_of_the_day));
			sb.append(",number_of_vehicles_involved=" + String.valueOf(number_of_vehicles_involved));
			sb.append(",property_damage=" + property_damage);
			sb.append(",bodily_injuries=" + String.valueOf(bodily_injuries));
			sb.append(",witnesses=" + String.valueOf(witnesses));
			sb.append(",police_report_available=" + police_report_available);
			sb.append(",total_claim_amount=" + String.valueOf(total_claim_amount));
			sb.append(",injury_claim=" + String.valueOf(injury_claim));
			sb.append(",property_claim=" + String.valueOf(property_claim));
			sb.append(",vehicle_claim=" + String.valueOf(vehicle_claim));
			sb.append(",auto_make=" + auto_make);
			sb.append(",auto_model=" + auto_model);
			sb.append(",auto_year=" + String.valueOf(auto_year));
			sb.append(",fraud_reported=" + String.valueOf(fraud_reported));
			sb.append(",_c39=" + _c39);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row2Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputDelimited_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row2Struct row2 = new row2Struct();
				out1Struct out1 = new out1Struct();

				/**
				 * [tLogRow_1 begin ] start
				 */

				ok_Hash.put("tLogRow_1", false);
				start_Hash.put("tLogRow_1", System.currentTimeMillis());

				currentComponent = "tLogRow_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "out1");
				}

				int tos_count_tLogRow_1 = 0;

				///////////////////////

				class Util_tLogRow_1 {

					String[] des_top = { ".", ".", "-", "+" };

					String[] des_head = { "|=", "=|", "-", "+" };

					String[] des_bottom = { "'", "'", "-", "+" };

					String name = "";

					java.util.List<String[]> list = new java.util.ArrayList<String[]>();

					int[] colLengths = new int[6];

					public void addRow(String[] row) {

						for (int i = 0; i < 6; i++) {
							if (row[i] != null) {
								colLengths[i] = Math.max(colLengths[i], row[i].length());
							}
						}
						list.add(row);
					}

					public void setTableName(String name) {

						this.name = name;
					}

					public StringBuilder format() {

						StringBuilder sb = new StringBuilder();

						sb.append(print(des_top));

						int totals = 0;
						for (int i = 0; i < colLengths.length; i++) {
							totals = totals + colLengths[i];
						}

						// name
						sb.append("|");
						int k = 0;
						for (k = 0; k < (totals + 5 - name.length()) / 2; k++) {
							sb.append(' ');
						}
						sb.append(name);
						for (int i = 0; i < totals + 5 - name.length() - k; i++) {
							sb.append(' ');
						}
						sb.append("|\n");

						// head and rows
						sb.append(print(des_head));
						for (int i = 0; i < list.size(); i++) {

							String[] row = list.get(i);

							java.util.Formatter formatter = new java.util.Formatter(new StringBuilder());

							StringBuilder sbformat = new StringBuilder();
							sbformat.append("|%1$-");
							sbformat.append(colLengths[0]);
							sbformat.append("s");

							sbformat.append("|%2$-");
							sbformat.append(colLengths[1]);
							sbformat.append("s");

							sbformat.append("|%3$-");
							sbformat.append(colLengths[2]);
							sbformat.append("s");

							sbformat.append("|%4$-");
							sbformat.append(colLengths[3]);
							sbformat.append("s");

							sbformat.append("|%5$-");
							sbformat.append(colLengths[4]);
							sbformat.append("s");

							sbformat.append("|%6$-");
							sbformat.append(colLengths[5]);
							sbformat.append("s");

							sbformat.append("|\n");

							formatter.format(sbformat.toString(), (Object[]) row);

							sb.append(formatter.toString());
							if (i == 0)
								sb.append(print(des_head)); // print the head
						}

						// end
						sb.append(print(des_bottom));
						return sb;
					}

					private StringBuilder print(String[] fillChars) {
						StringBuilder sb = new StringBuilder();
						// first column
						sb.append(fillChars[0]);
						for (int i = 0; i < colLengths[0] - fillChars[0].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);

						for (int i = 0; i < colLengths[1] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[2] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[3] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[4] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);

						// last column
						for (int i = 0; i < colLengths[5] - fillChars[1].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[1]);
						sb.append("\n");
						return sb;
					}

					public boolean isTableEmpty() {
						if (list.size() > 1)
							return false;
						return true;
					}
				}
				Util_tLogRow_1 util_tLogRow_1 = new Util_tLogRow_1();
				util_tLogRow_1.setTableName("tLogRow_1");
				util_tLogRow_1.addRow(new String[] { "collision_type", "property_damage", "police_report_available",
						"fraud_reported", "incident_date", "policy_bind_date", });
				StringBuilder strBuffer_tLogRow_1 = null;
				int nb_line_tLogRow_1 = 0;
///////////////////////    			

				/**
				 * [tLogRow_1 begin ] stop
				 */

				/**
				 * [tMap_1 begin ] start
				 */

				ok_Hash.put("tMap_1", false);
				start_Hash.put("tMap_1", System.currentTimeMillis());

				currentComponent = "tMap_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row2");
				}

				int tos_count_tMap_1 = 0;

// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_1__Struct {
					String var2;
					String collision_type;
					String var3;
					String property_damage;
					String police_report_available;
					int fraud_reported;
					java.util.Date incident_date;
				}
				Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
// ###############################

// ###############################
// # Outputs initialization
				out1Struct out1_tmp = new out1Struct();
// ###############################

				/**
				 * [tMap_1 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_1 begin ] start
				 */

				ok_Hash.put("tFileInputDelimited_1", false);
				start_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_1";

				int tos_count_tFileInputDelimited_1 = 0;

				final routines.system.RowState rowstate_tFileInputDelimited_1 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_1 = 0;
				org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_1 = null;
				int limit_tFileInputDelimited_1 = -1;
				try {

					Object filename_tFileInputDelimited_1 = "E:/GI5 BI/Insurance claims — Fraud detection/insurance_claims.csv";
					if (filename_tFileInputDelimited_1 instanceof java.io.InputStream) {

						int footer_value_tFileInputDelimited_1 = 0, random_value_tFileInputDelimited_1 = -1;
						if (footer_value_tFileInputDelimited_1 > 0 || random_value_tFileInputDelimited_1 > 0) {
							throw new java.lang.Exception(
									"When the input source is a stream,footer and random shouldn't be bigger than 0.");
						}

					}
					try {
						fid_tFileInputDelimited_1 = new org.talend.fileprocess.FileInputDelimited(
								"E:/GI5 BI/Insurance claims — Fraud detection/insurance_claims.csv", "US-ASCII", ",",
								"\n", false, 1, 0, limit_tFileInputDelimited_1, -1, false);
					} catch (java.lang.Exception e) {
						globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());

						System.err.println(e.getMessage());

					}

					while (fid_tFileInputDelimited_1 != null && fid_tFileInputDelimited_1.nextRecord()) {
						rowstate_tFileInputDelimited_1.reset();

						row2 = null;

						boolean whetherReject_tFileInputDelimited_1 = false;
						row2 = new row2Struct();
						try {

							int columnIndexWithD_tFileInputDelimited_1 = 0;

							String temp = "";

							columnIndexWithD_tFileInputDelimited_1 = 0;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.months_as_customer = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"months_as_customer", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.months_as_customer = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 1;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.age = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"age", "row2", temp, ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
								}

							} else {

								row2.age = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 2;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.policy_number = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"policy_number", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.policy_number = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 3;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.policy_bind_date = ParserUtils.parseTo_Date(temp, "yyyy-MM-dd");

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"policy_bind_date", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.policy_bind_date = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 4;

							row2.policy_state = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 5;

							row2.policy_csl = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 6;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.policy_deductable = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"policy_deductable", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.policy_deductable = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 7;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.policy_annual_premium = ParserUtils.parseTo_Float(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"policy_annual_premium", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.policy_annual_premium = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 8;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.umbrella_limit = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"umbrella_limit", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.umbrella_limit = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 9;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.insured_zip = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"insured_zip", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.insured_zip = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 10;

							row2.insured_sex = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 11;

							row2.insured_education_level = fid_tFileInputDelimited_1
									.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 12;

							row2.insured_occupation = fid_tFileInputDelimited_1
									.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 13;

							row2.insured_hobbies = fid_tFileInputDelimited_1
									.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 14;

							row2.insured_relationship = fid_tFileInputDelimited_1
									.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 15;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.capital_gains = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"capital_gains", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.capital_gains = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 16;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.capital_loss = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"capital_loss", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.capital_loss = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 17;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.incident_date = ParserUtils.parseTo_Date(temp, "yyyy-MM-dd");

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"incident_date", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.incident_date = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 18;

							row2.incident_type = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 19;

							row2.collision_type = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 20;

							row2.incident_severity = fid_tFileInputDelimited_1
									.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 21;

							row2.authorities_contacted = fid_tFileInputDelimited_1
									.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 22;

							row2.incident_state = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 23;

							row2.incident_city = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 24;

							row2.incident_location = fid_tFileInputDelimited_1
									.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 25;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.incident_hour_of_the_day = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"incident_hour_of_the_day", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.incident_hour_of_the_day = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 26;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.number_of_vehicles_involved = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"number_of_vehicles_involved", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.number_of_vehicles_involved = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 27;

							row2.property_damage = fid_tFileInputDelimited_1
									.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 28;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.bodily_injuries = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"bodily_injuries", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.bodily_injuries = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 29;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.witnesses = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"witnesses", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.witnesses = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 30;

							row2.police_report_available = fid_tFileInputDelimited_1
									.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 31;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.total_claim_amount = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"total_claim_amount", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.total_claim_amount = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 32;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.injury_claim = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"injury_claim", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.injury_claim = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 33;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.property_claim = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"property_claim", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.property_claim = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 34;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.vehicle_claim = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"vehicle_claim", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.vehicle_claim = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 35;

							row2.auto_make = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 36;

							row2.auto_model = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 37;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.auto_year = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"auto_year", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.auto_year = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 38;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row2.fraud_reported = ParserUtils.parseTo_Character(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"fraud_reported", "row2", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row2.fraud_reported = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 39;

							row2._c39 = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							if (rowstate_tFileInputDelimited_1.getException() != null) {
								throw rowstate_tFileInputDelimited_1.getException();
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputDelimited_1 = true;

							System.err.println(e.getMessage());
							row2 = null;

						}

						/**
						 * [tFileInputDelimited_1 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_1 main ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						tos_count_tFileInputDelimited_1++;

						/**
						 * [tFileInputDelimited_1 main ] stop
						 */

						/**
						 * [tFileInputDelimited_1 process_data_begin ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						/**
						 * [tFileInputDelimited_1 process_data_begin ] stop
						 */
// Start of branch "row2"
						if (row2 != null) {

							/**
							 * [tMap_1 main ] start
							 */

							currentComponent = "tMap_1";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row2"

								);
							}

							boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;

							// ###############################
							// # Input tables (lookups)
							boolean rejectedInnerJoin_tMap_1 = false;
							boolean mainRowRejected_tMap_1 = false;

							// ###############################
							{ // start of Var scope

								// ###############################
								// # Vars tables

								Var__tMap_1__Struct Var = Var__tMap_1;
								Var.var2 = Var.var2 == "Front collision" ? "Rear collision"
										: Var.var2 == "Rear collision" ? "Side collision" : "Front Collision";
								Var.collision_type = StringHandling.CHANGE(row2.collision_type, "\\?", Var.var2);
								;
								Var.var3 = Var.var3 = Var.var3 == "1" ? "0" : "1";
								;
								Var.property_damage = (row2.property_damage).equals("?") && Var.var3 == "1" ? "1"
										: (row2.property_damage).equals("?") && Var.var3 == "0" ? "0"
												: (row2.property_damage).equals("YES") ? "1" : "0";
								Var.police_report_available = (row2.police_report_available).equals("\\?")
										|| (row2.police_report_available).equals("YES") ? "1" : "0";
								Var.fraud_reported = row2.fraud_reported == 'Y' ? 1 : 0;
								Var.incident_date = row2.incident_date;// ###############################
								// ###############################
								// # Output tables

								out1 = null;

// # Output table : 'out1'
								out1_tmp.collision_type = Var.collision_type;
								out1_tmp.property_damage = Var.property_damage;
								out1_tmp.police_report_available = Var.police_report_available;
								out1_tmp.fraud_reported = Var.fraud_reported;
								out1_tmp.incident_date = Var.incident_date;
								out1_tmp.policy_bind_date = row2.policy_bind_date;
								out1 = out1_tmp;
// ###############################

							} // end of Var scope

							rejectedInnerJoin_tMap_1 = false;

							tos_count_tMap_1++;

							/**
							 * [tMap_1 main ] stop
							 */

							/**
							 * [tMap_1 process_data_begin ] start
							 */

							currentComponent = "tMap_1";

							/**
							 * [tMap_1 process_data_begin ] stop
							 */
// Start of branch "out1"
							if (out1 != null) {

								/**
								 * [tLogRow_1 main ] start
								 */

								currentComponent = "tLogRow_1";

								if (execStat) {
									runStat.updateStatOnConnection(iterateId, 1, 1

											, "out1"

									);
								}

///////////////////////		

								String[] row_tLogRow_1 = new String[6];

								if (out1.collision_type != null) { //
									row_tLogRow_1[0] = String.valueOf(out1.collision_type);

								} //

								if (out1.property_damage != null) { //
									row_tLogRow_1[1] = String.valueOf(out1.property_damage);

								} //

								if (out1.police_report_available != null) { //
									row_tLogRow_1[2] = String.valueOf(out1.police_report_available);

								} //

								row_tLogRow_1[3] = String.valueOf(out1.fraud_reported);

								if (out1.incident_date != null) { //
									row_tLogRow_1[4] = FormatterUtils.format_Date(out1.incident_date, "yyyy-MM-dd");

								} //

								if (out1.policy_bind_date != null) { //
									row_tLogRow_1[5] = FormatterUtils.format_Date(out1.policy_bind_date, "yyyy-MM-dd");

								} //

								util_tLogRow_1.addRow(row_tLogRow_1);
								nb_line_tLogRow_1++;
//////

//////                    

///////////////////////    			

								tos_count_tLogRow_1++;

								/**
								 * [tLogRow_1 main ] stop
								 */

								/**
								 * [tLogRow_1 process_data_begin ] start
								 */

								currentComponent = "tLogRow_1";

								/**
								 * [tLogRow_1 process_data_begin ] stop
								 */

								/**
								 * [tLogRow_1 process_data_end ] start
								 */

								currentComponent = "tLogRow_1";

								/**
								 * [tLogRow_1 process_data_end ] stop
								 */

							} // End of branch "out1"

							/**
							 * [tMap_1 process_data_end ] start
							 */

							currentComponent = "tMap_1";

							/**
							 * [tMap_1 process_data_end ] stop
							 */

						} // End of branch "row2"

						/**
						 * [tFileInputDelimited_1 process_data_end ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						/**
						 * [tFileInputDelimited_1 process_data_end ] stop
						 */

						/**
						 * [tFileInputDelimited_1 end ] start
						 */

						currentComponent = "tFileInputDelimited_1";

					}
				} finally {
					if (!((Object) ("E:/GI5 BI/Insurance claims — Fraud detection/insurance_claims.csv") instanceof java.io.InputStream)) {
						if (fid_tFileInputDelimited_1 != null) {
							fid_tFileInputDelimited_1.close();
						}
					}
					if (fid_tFileInputDelimited_1 != null) {
						globalMap.put("tFileInputDelimited_1_NB_LINE", fid_tFileInputDelimited_1.getRowNumber());

					}
				}

				ok_Hash.put("tFileInputDelimited_1", true);
				end_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_1 end ] stop
				 */

				/**
				 * [tMap_1 end ] start
				 */

				currentComponent = "tMap_1";

// ###############################
// # Lookup hashes releasing
// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row2");
				}

				ok_Hash.put("tMap_1", true);
				end_Hash.put("tMap_1", System.currentTimeMillis());

				/**
				 * [tMap_1 end ] stop
				 */

				/**
				 * [tLogRow_1 end ] start
				 */

				currentComponent = "tLogRow_1";

//////

				java.io.PrintStream consoleOut_tLogRow_1 = null;
				if (globalMap.get("tLogRow_CONSOLE") != null) {
					consoleOut_tLogRow_1 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
				} else {
					consoleOut_tLogRow_1 = new java.io.PrintStream(new java.io.BufferedOutputStream(System.out));
					globalMap.put("tLogRow_CONSOLE", consoleOut_tLogRow_1);
				}

				consoleOut_tLogRow_1.println(util_tLogRow_1.format().toString());
				consoleOut_tLogRow_1.flush();
//////
				globalMap.put("tLogRow_1_NB_LINE", nb_line_tLogRow_1);

///////////////////////    			

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "out1");
				}

				ok_Hash.put("tLogRow_1", true);
				end_Hash.put("tLogRow_1", System.currentTimeMillis());

				/**
				 * [tLogRow_1 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tFileInputDelimited_1 finally ] start
				 */

				currentComponent = "tFileInputDelimited_1";

				/**
				 * [tFileInputDelimited_1 finally ] stop
				 */

				/**
				 * [tMap_1 finally ] start
				 */

				currentComponent = "tMap_1";

				/**
				 * [tMap_1 finally ] stop
				 */

				/**
				 * [tLogRow_1 finally ] start
				 */

				currentComponent = "tLogRow_1";

				/**
				 * [tLogRow_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 1);
	}

	public String resuming_logs_dir_path = null;
	public String resuming_checkpoint_path = null;
	public String parent_part_launcher = null;
	private String resumeEntryMethodName = null;
	private boolean globalResumeTicket = false;

	public boolean watch = false;
	// portStats is null, it means don't execute the statistics
	public Integer portStats = null;
	public int portTraces = 4334;
	public String clientHost;
	public String defaultClientHost = "localhost";
	public String contextStr = "Default";
	public boolean isDefaultContext = true;
	public String pid = "0";
	public String rootPid = null;
	public String fatherPid = null;
	public String fatherNode = null;
	public long startTime = 0;
	public boolean isChildJob = false;
	public String log4jLevel = "";

	private boolean enableLogStash;

	private boolean execStat = true;

	private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
		protected java.util.Map<String, String> initialValue() {
			java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
			threadRunResultMap.put("errorCode", null);
			threadRunResultMap.put("status", "");
			return threadRunResultMap;
		};
	};

	protected PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	public static void main(String[] args) {
		final csv_to_db csv_to_dbClass = new csv_to_db();

		int exitCode = csv_to_dbClass.runJobInTOS(args);

		System.exit(exitCode);
	}

	public String[][] runJob(String[] args) {

		int exitCode = runJobInTOS(args);
		String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

		return bufferValue;
	}

	public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;

		return hastBufferOutput;
	}

	public int runJobInTOS(String[] args) {
		// reset status
		status = "";

		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}
		enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		if (pid == null || "0".equals(pid)) {
			pid = TalendString.getAsciiRandomString(6);
		}

		if (rootPid == null) {
			rootPid = pid;
		}
		if (fatherPid == null) {
			fatherPid = pid;
		} else {
			isChildJob = true;
		}

		if (portStats != null) {
			// portStats = -1; //for testing
			if (portStats < 0 || portStats > 65535) {
				// issue:10869, the portStats is invalid, so this client socket can't open
				System.err.println("The statistics socket port " + portStats + " is invalid.");
				execStat = false;
			}
		} else {
			execStat = false;
		}
		boolean inOSGi = routines.system.BundleUtils.inOSGi();

		if (inOSGi) {
			java.util.Dictionary<String, Object> jobProperties = routines.system.BundleUtils.getJobProperties(jobName);

			if (jobProperties != null && jobProperties.get("context") != null) {
				contextStr = (String) jobProperties.get("context");
			}
		}

		try {
			// call job/subjob with an existing context, like: --context=production. if
			// without this parameter, there will use the default context instead.
			java.io.InputStream inContext = csv_to_db.class.getClassLoader().getResourceAsStream(
					"businessintelligenceminiprojet/csv_to_db_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = csv_to_db.class.getClassLoader()
						.getResourceAsStream("config/contexts/" + contextStr + ".properties");
			}
			if (inContext != null) {
				try {
					// defaultProps is in order to keep the original context value
					if (context != null && context.isEmpty()) {
						defaultProps.load(inContext);
						context = new ContextProperties(defaultProps);
					}
				} finally {
					inContext.close();
				}
			} else if (!isDefaultContext) {
				// print info and job continue to run, for case: context_param is not empty.
				System.err.println("Could not find the context " + contextStr);
			}

			if (!context_param.isEmpty()) {
				context.putAll(context_param);
				// set types for params from parentJobs
				for (Object key : context_param.keySet()) {
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
			}
			class ContextProcessing {
				private void processContext_0() {
				}

				public void processAllContext() {
					processContext_0();
				}
			}

			new ContextProcessing().processAllContext();
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		// get context value from parent directly
		if (parentContextMap != null && !parentContextMap.isEmpty()) {
		}

		// Resume: init the resumeUtil
		resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
		resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
		resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
		// Resume: jobStart
		resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "",
				"", "", "", "", resumeUtil.convertToJsonText(context, parametersToEncrypt));

		if (execStat) {
			try {
				runStat.openSocket(!isChildJob);
				runStat.setAllPID(rootPid, fatherPid, pid, jobName);
				runStat.startThreadStat(clientHost, portStats);
				runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
			} catch (java.io.IOException ioException) {
				ioException.printStackTrace();
			}
		}

		java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
		globalMap.put("concurrentHashMap", concurrentHashMap);

		long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;

		startTime = System.currentTimeMillis();

		this.globalResumeTicket = true;// to run tPreJob

		this.globalResumeTicket = false;// to run others jobs

		try {
			errorCode = null;
			tDBInput_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tDBInput_1) {
			globalMap.put("tDBInput_1_SUBPROCESS_STATE", -1);

			e_tDBInput_1.printStackTrace();

		}
		try {
			errorCode = null;
			tFileInputDelimited_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tFileInputDelimited_1) {
			globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", -1);

			e_tFileInputDelimited_1.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : csv_to_db");
		}

		if (execStat) {
			runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
			runStat.stopThreadStat();
		}
		int returnCode = 0;

		if (errorCode == null) {
			returnCode = status != null && status.equals("failure") ? 1 : 0;
		} else {
			returnCode = errorCode.intValue();
		}
		resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
				"" + returnCode, "", "", "");

		return returnCode;

	}

	// only for OSGi env
	public void destroy() {

	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();

		return connections;
	}

	private void evalParam(String arg) {
		if (arg.startsWith("--resuming_logs_dir_path")) {
			resuming_logs_dir_path = arg.substring(25);
		} else if (arg.startsWith("--resuming_checkpoint_path")) {
			resuming_checkpoint_path = arg.substring(27);
		} else if (arg.startsWith("--parent_part_launcher")) {
			parent_part_launcher = arg.substring(23);
		} else if (arg.startsWith("--watch")) {
			watch = true;
		} else if (arg.startsWith("--stat_port=")) {
			String portStatsStr = arg.substring(12);
			if (portStatsStr != null && !portStatsStr.equals("null")) {
				portStats = Integer.parseInt(portStatsStr);
			}
		} else if (arg.startsWith("--trace_port=")) {
			portTraces = Integer.parseInt(arg.substring(13));
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
			isDefaultContext = false;
		} else if (arg.startsWith("--father_pid=")) {
			fatherPid = arg.substring(13);
		} else if (arg.startsWith("--root_pid=")) {
			rootPid = arg.substring(11);
		} else if (arg.startsWith("--father_node=")) {
			fatherNode = arg.substring(14);
		} else if (arg.startsWith("--pid=")) {
			pid = arg.substring(6);
		} else if (arg.startsWith("--context_type")) {
			String keyValue = arg.substring(15);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.setContextType(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
				}

			}

		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
				}
			}
		} else if (arg.startsWith("--log4jLevel=")) {
			log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {// for trunjob call
			final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
	}

	private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

	private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" }, { "\\'", "\'" }, { "\\r", "\r" },
			{ "\\f", "\f" }, { "\\b", "\b" }, { "\\t", "\t" } };

	private String replaceEscapeChars(String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0], currIndex);
				if (index >= 0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0],
							strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the
			// result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getStatus() {
		return status;
	}

	ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 * 166626 characters generated by Talend Open Studio for Data Integration on the
 * 27 décembre 2023 à 17:42:29 WEST
 ************************************************************************************************/