package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsList;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.Property;
import io.realm.internal.ProxyUtils;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("all")
public class StepModelRealmProxy extends zblibrary.demo.stepModule.model.StepModel
    implements RealmObjectProxy, StepModelRealmProxyInterface {

    static final class StepModelColumnInfo extends ColumnInfo {
        long dateIndex;
        long numStepsIndex;

        StepModelColumnInfo(OsSchemaInfo schemaInfo) {
            super(2);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("StepModel");
            this.dateIndex = addColumnDetails("date", objectSchemaInfo);
            this.numStepsIndex = addColumnDetails("numSteps", objectSchemaInfo);
        }

        StepModelColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new StepModelColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final StepModelColumnInfo src = (StepModelColumnInfo) rawSrc;
            final StepModelColumnInfo dst = (StepModelColumnInfo) rawDst;
            dst.dateIndex = src.dateIndex;
            dst.numStepsIndex = src.numStepsIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(2);
        fieldNames.add("date");
        fieldNames.add("numSteps");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private StepModelColumnInfo columnInfo;
    private ProxyState<zblibrary.demo.stepModule.model.StepModel> proxyState;

    StepModelRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (StepModelColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<zblibrary.demo.stepModule.model.StepModel>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public Date realmGet$date() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.dateIndex)) {
            return null;
        }
        return (java.util.Date) proxyState.getRow$realm().getDate(columnInfo.dateIndex);
    }

    @Override
    public void realmSet$date(Date value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dateIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setDate(columnInfo.dateIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dateIndex);
            return;
        }
        proxyState.getRow$realm().setDate(columnInfo.dateIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$numSteps() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.numStepsIndex);
    }

    @Override
    public void realmSet$numSteps(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.numStepsIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.numStepsIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("StepModel", 2, 0);
        builder.addPersistedProperty("date", RealmFieldType.DATE, !Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("numSteps", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static StepModelColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new StepModelColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "StepModel";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static zblibrary.demo.stepModule.model.StepModel createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        zblibrary.demo.stepModule.model.StepModel obj = realm.createObjectInternal(zblibrary.demo.stepModule.model.StepModel.class, true, excludeFields);

        final StepModelRealmProxyInterface objProxy = (StepModelRealmProxyInterface) obj;
        if (json.has("date")) {
            if (json.isNull("date")) {
                objProxy.realmSet$date(null);
            } else {
                Object timestamp = json.get("date");
                if (timestamp instanceof String) {
                    objProxy.realmSet$date(JsonUtils.stringToDate((String) timestamp));
                } else {
                    objProxy.realmSet$date(new Date(json.getLong("date")));
                }
            }
        }
        if (json.has("numSteps")) {
            if (json.isNull("numSteps")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'numSteps' to null.");
            } else {
                objProxy.realmSet$numSteps((long) json.getLong("numSteps"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static zblibrary.demo.stepModule.model.StepModel createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final zblibrary.demo.stepModule.model.StepModel obj = new zblibrary.demo.stepModule.model.StepModel();
        final StepModelRealmProxyInterface objProxy = (StepModelRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("date")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$date(null);
                } else if (reader.peek() == JsonToken.NUMBER) {
                    long timestamp = reader.nextLong();
                    if (timestamp > -1) {
                        objProxy.realmSet$date(new Date(timestamp));
                    }
                } else {
                    objProxy.realmSet$date(JsonUtils.stringToDate(reader.nextString()));
                }
            } else if (name.equals("numSteps")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$numSteps((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'numSteps' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    public static zblibrary.demo.stepModule.model.StepModel copyOrUpdate(Realm realm, zblibrary.demo.stepModule.model.StepModel object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null) {
            final BaseRealm otherRealm = ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm();
            if (otherRealm.threadId != realm.threadId) {
                throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
            }
            if (otherRealm.getPath().equals(realm.getPath())) {
                return object;
            }
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (zblibrary.demo.stepModule.model.StepModel) cachedRealmObject;
        }

        return copy(realm, object, update, cache);
    }

    public static zblibrary.demo.stepModule.model.StepModel copy(Realm realm, zblibrary.demo.stepModule.model.StepModel newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (zblibrary.demo.stepModule.model.StepModel) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        zblibrary.demo.stepModule.model.StepModel realmObject = realm.createObjectInternal(zblibrary.demo.stepModule.model.StepModel.class, false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        StepModelRealmProxyInterface realmObjectSource = (StepModelRealmProxyInterface) newObject;
        StepModelRealmProxyInterface realmObjectCopy = (StepModelRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$date(realmObjectSource.realmGet$date());
        realmObjectCopy.realmSet$numSteps(realmObjectSource.realmGet$numSteps());
        return realmObject;
    }

    public static long insert(Realm realm, zblibrary.demo.stepModule.model.StepModel object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(zblibrary.demo.stepModule.model.StepModel.class);
        long tableNativePtr = table.getNativePtr();
        StepModelColumnInfo columnInfo = (StepModelColumnInfo) realm.getSchema().getColumnInfo(zblibrary.demo.stepModule.model.StepModel.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);
        java.util.Date realmGet$date = ((StepModelRealmProxyInterface) object).realmGet$date();
        if (realmGet$date != null) {
            Table.nativeSetTimestamp(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date.getTime(), false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.numStepsIndex, rowIndex, ((StepModelRealmProxyInterface) object).realmGet$numSteps(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(zblibrary.demo.stepModule.model.StepModel.class);
        long tableNativePtr = table.getNativePtr();
        StepModelColumnInfo columnInfo = (StepModelColumnInfo) realm.getSchema().getColumnInfo(zblibrary.demo.stepModule.model.StepModel.class);
        zblibrary.demo.stepModule.model.StepModel object = null;
        while (objects.hasNext()) {
            object = (zblibrary.demo.stepModule.model.StepModel) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);
            java.util.Date realmGet$date = ((StepModelRealmProxyInterface) object).realmGet$date();
            if (realmGet$date != null) {
                Table.nativeSetTimestamp(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date.getTime(), false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.numStepsIndex, rowIndex, ((StepModelRealmProxyInterface) object).realmGet$numSteps(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, zblibrary.demo.stepModule.model.StepModel object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(zblibrary.demo.stepModule.model.StepModel.class);
        long tableNativePtr = table.getNativePtr();
        StepModelColumnInfo columnInfo = (StepModelColumnInfo) realm.getSchema().getColumnInfo(zblibrary.demo.stepModule.model.StepModel.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);
        java.util.Date realmGet$date = ((StepModelRealmProxyInterface) object).realmGet$date();
        if (realmGet$date != null) {
            Table.nativeSetTimestamp(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date.getTime(), false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dateIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.numStepsIndex, rowIndex, ((StepModelRealmProxyInterface) object).realmGet$numSteps(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(zblibrary.demo.stepModule.model.StepModel.class);
        long tableNativePtr = table.getNativePtr();
        StepModelColumnInfo columnInfo = (StepModelColumnInfo) realm.getSchema().getColumnInfo(zblibrary.demo.stepModule.model.StepModel.class);
        zblibrary.demo.stepModule.model.StepModel object = null;
        while (objects.hasNext()) {
            object = (zblibrary.demo.stepModule.model.StepModel) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);
            java.util.Date realmGet$date = ((StepModelRealmProxyInterface) object).realmGet$date();
            if (realmGet$date != null) {
                Table.nativeSetTimestamp(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date.getTime(), false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dateIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.numStepsIndex, rowIndex, ((StepModelRealmProxyInterface) object).realmGet$numSteps(), false);
        }
    }

    public static zblibrary.demo.stepModule.model.StepModel createDetachedCopy(zblibrary.demo.stepModule.model.StepModel realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        zblibrary.demo.stepModule.model.StepModel unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new zblibrary.demo.stepModule.model.StepModel();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (zblibrary.demo.stepModule.model.StepModel) cachedObject.object;
            }
            unmanagedObject = (zblibrary.demo.stepModule.model.StepModel) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        StepModelRealmProxyInterface unmanagedCopy = (StepModelRealmProxyInterface) unmanagedObject;
        StepModelRealmProxyInterface realmSource = (StepModelRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$date(realmSource.realmGet$date());
        unmanagedCopy.realmSet$numSteps(realmSource.realmGet$numSteps());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("StepModel = proxy[");
        stringBuilder.append("{date:");
        stringBuilder.append(realmGet$date() != null ? realmGet$date() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{numSteps:");
        stringBuilder.append(realmGet$numSteps());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StepModelRealmProxy aStepModel = (StepModelRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aStepModel.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aStepModel.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aStepModel.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
