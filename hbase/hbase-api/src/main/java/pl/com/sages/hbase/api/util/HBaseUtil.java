package pl.com.sages.hbase.api.util;

import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public abstract class HBaseUtil {

    public static void recreateTable(String tableName, String familyName) {
        recreateTable(TableName.valueOf(tableName), familyName);
    }

    public static void recreateTable(TableName tableName, byte[] familyName) {
        recreateTable(tableName, Bytes.toString(familyName));
    }

    public static void recreateTable(TableName tableName, String familyName) {
        new HBaseTableBuilder().withTable(tableName).withFamily(familyName).build();
    }

    public static TableName getUserTableName(String tableName) {
        String userName = System.getProperty("user.name");
        return TableName.valueOf(userName, tableName);
    }

    public static void createNamespaceIfNotExists(final String namespace, final Admin admin) throws IOException {
        NamespaceDescriptor namespaceDescriptor;
        if (!HBaseUtil.namespaceExists(namespace, admin)) {
            namespaceDescriptor = NamespaceDescriptor.create(namespace).build();
            admin.createNamespace(namespaceDescriptor);
        }
    }

    public static boolean namespaceExists(final String namespace, final Admin admin) throws IOException {
        NamespaceDescriptor[] namespaceDescriptors = admin.listNamespaceDescriptors();
        for (NamespaceDescriptor namespaceDescriptor : namespaceDescriptors) {
            if (namespaceDescriptor.getName().equals(namespace)) {
                return true;
            }
        }
        return false;
    }

}
