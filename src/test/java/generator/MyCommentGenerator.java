package generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.util.StringUtility;
import tk.mybatis.mapper.generator.MapperCommentGenerator;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Properties;

/**
 * 复写生成model的方法，加入自定义注释和注解
 * ceate by xujingyang
 * ceate date 2018/07/01
 */
public class MyCommentGenerator extends MapperCommentGenerator {

    private String beginningDelimiter = "";
    private String endingDelimiter = "";
    private boolean forceAnnotation;

    @Override
    public void addConfigurationProperties(Properties properties) {
        String beginningDelimiter = properties.getProperty("beginningDelimiter");
        if (StringUtility.stringHasValue(beginningDelimiter)) {
            this.beginningDelimiter = beginningDelimiter;
        }

        String endingDelimiter = properties.getProperty("endingDelimiter");
        if (StringUtility.stringHasValue(endingDelimiter)) {
            this.endingDelimiter = endingDelimiter;
        }

        String forceAnnotation = properties.getProperty("forceAnnotation");
        if (StringUtility.stringHasValue(forceAnnotation)) {
            this.forceAnnotation = forceAnnotation.equalsIgnoreCase("TRUE");
        }

    }


    /*添加类说明或者注解都在这里*/
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String[] split = introspectedTable.getBaseRecordType().split("\\.");
        topLevelClass.addAnnotation("@ApiModel(value = \"" + split[split.length - 1] + "\",description = \"" + introspectedTable.getRemarks() + "\")");
        topLevelClass.addSuperInterface(new FullyQualifiedJavaType("Serializable"));
    }


    /*添加字段注释、注解、更改字段类型等*/
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

        if (field.getType().getShortName().equalsIgnoreCase("Date")) {
            field.setType(new FullyQualifiedJavaType("LocalDateTime"));
            introspectedColumn.setFullyQualifiedJavaType(new FullyQualifiedJavaType("LocalDateTime"));

        }

        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("/**");
            StringBuilder sb = new StringBuilder();
            sb.append(" * ");
            sb.append(introspectedColumn.getRemarks());
            field.addJavaDocLine(sb.toString());
            field.addJavaDocLine(" */");
            field.addAnnotation("@ApiModelProperty(\"" + introspectedColumn.getRemarks().replaceAll("\r|\n", "") + "\")");
        }

        if (field.isTransient()) {
            field.addAnnotation("@Transient");
        }

        Iterator var7 = introspectedTable.getPrimaryKeyColumns().iterator();

        while (var7.hasNext()) {
            IntrospectedColumn column = (IntrospectedColumn) var7.next();
            if (introspectedColumn == column) {
                field.addAnnotation("@Id");
                break;
            }
        }

        String column = introspectedColumn.getActualColumnName();
        if (StringUtility.stringContainsSpace(column) || introspectedTable.getTableConfiguration().isAllColumnDelimitingEnabled()) {
            column = introspectedColumn.getContext().getBeginningDelimiter() + column + introspectedColumn.getContext().getEndingDelimiter();
        }

        if (!column.equals(introspectedColumn.getJavaProperty())) {
            field.addAnnotation("@Column(name = \"" + this.getDelimiterName(column) + "\")");
        } else if (!StringUtility.stringHasValue(beginningDelimiter) && !StringUtility.stringHasValue(this.endingDelimiter)) {
            if (this.forceAnnotation) {
                field.addAnnotation("@Column(name = \"" + this.getDelimiterName(column) + "\")");
            }
        } else {
            field.addAnnotation("@Column(name = \"" + this.getDelimiterName(column) + "\")");
        }

        if (introspectedColumn.isIdentity()) {
            if (introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement().equals("JDBC")) {
                field.addAnnotation("@GeneratedValue(generator = \"JDBC\")");
            } else {
                field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY)");
            }
        } else if (introspectedColumn.isSequenceColumn()) {
            String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
            String sql = MessageFormat.format(introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement(), tableName, tableName.toUpperCase());
            field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY, generator = \"" + sql + "\")");
        }

    }


    /*getter方法的注释等*/
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

        StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**");
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            sb.append(" * 获取");
            sb.append(introspectedColumn.getRemarks());
            method.addJavaDocLine(sb.toString());
            method.addJavaDocLine(" *");
        }

        sb.setLength(0);
        sb.append(" * @return ");
        sb.append(introspectedColumn.getActualColumnName());
        /*if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            sb.append(" - ");
            sb.append(introspectedColumn.getRemarks());
        }*/

        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */");
    }

    /*setter方法的注释等*/
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

        StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**");
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            sb.append(" * 设置");
            sb.append(introspectedColumn.getRemarks());
            method.addJavaDocLine(sb.toString());
            method.addJavaDocLine(" *");
        }

        Parameter parm = (Parameter) method.getParameters().get(0);
        sb.setLength(0);
        sb.append(" * @param ");
        sb.append(parm.getName());
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            sb.append(" ");
            sb.append(introspectedColumn.getRemarks());
        }

        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */");
    }


}
