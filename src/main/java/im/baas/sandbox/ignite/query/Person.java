package im.baas.sandbox.ignite.query;

import lombok.Data;
import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;

import java.io.Serializable;

@Data
public class Person implements Serializable {
    /** Person ID (indexed). */
    @QuerySqlField(index = true)
    private long id;
    /** Organization ID (indexed). */
    @QuerySqlField(index = true)
    private long orgId;
    /** First name (not-indexed). */
    @QuerySqlField
    private String firstName;
    /** Last name (not indexed). */
    @QuerySqlField
    private String lastName;
    /** Resume text (create LUCENE-based TEXT index for this field). */
    @QueryTextField
    private String resume;
    /** Salary (indexed). */
    @QuerySqlField(index = true)
    private double salary;
}