<#assign crud_columns = [] />
<#assign columns = table.columnList />
<#list columns as col>
    <#if !(col.columnName =='id' || col.columnName == 'created' || col.columnName == 'modified')>
        <#assign crud_columns = crud_columns + [col] />
    </#if>
</#list>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${cfg.beanMapperPackage}.${table.beanName}Mapper">
    <!--查询字段-->
    <sql id="columns">
        <#list columns as col>
        <#if col.columnName!='is_delete'>
            a.${col.columnName}<#if col_has_next>,</#if>
        </#if>
        </#list>
    </sql>

    <!--查询结果集-->
    <resultMap id="beanMap" type="${cfg.baseDbBeanPackage}.${table.beanName}">
        <#list columns as col>
        <#if col.columnName!='is_delete'>
            <result property="${col.propertyName}" column="${col.columnName}"/>
        </#if>
        </#list>
    </resultMap>

    <!--根据主键获取实体-->
    <select id="get" resultMap="beanMap">
        SELECT
        <include refid="columns"/>
        FROM
        ${table.tableName} a
            WHERE
        <#list table.pkColumnList as pk><#if pk_index gt 0> AND</#if> ${pk} = <#noparse>#{</#noparse>${table.pkPropertyList[pk_index]}<#noparse>}</#noparse></#list>
        <#list columns as col>
            <#if col.columnName=='is_delete'>
            AND ${col.columnName} = 0
            </#if>
        </#list>
    </select>

    <!-- 新增记录 -->
    <insert id="add" useGeneratedKeys="true" keyProperty="id"
            parameterType="${cfg.baseDbBeanPackage}.${table.beanName}">
        INSERT INTO ${table.tableName}
            (
        <#list columns as col>
        <#if col.columnName!='is_delete'>
            `${col.columnName}`<#sep>,
        </#if>
        </#list>
            )
            VALUES
            (
        <#list columns as col>
            <#if col.columnName!='is_delete'>
            <#noparse>#{</#noparse>${col.propertyName}<#noparse>}</#noparse><#sep>,
            </#if>
        </#list>

        )
    </insert>


    <!--更新实体信息-->
    <update id="update" parameterType="${cfg.baseDbBeanPackage}.${table.beanName}">
        UPDATE ${table.tableName}
        <set>
            <#list crud_columns as col>
            <#if col.columnName!='is_delete'>
                <if test="${col.propertyName} != null">
            `${col.columnName}` = <#noparse>#{</#noparse>${col.propertyName}<#noparse>}</#noparse>,
                </if>
            </#if>

            </#list>
        </set>
        WHERE <#list table.pkColumnList as pk><#if pk_index gt 0> AND</#if> ${pk} = <#noparse>#{</#noparse>${table.pkPropertyList[pk_index]}<#noparse>}</#noparse></#list>
        <#list columns as col>
            <#if col.columnName=='is_delete'>
            AND ${col.columnName} = 0
            </#if>
        </#list>
    </update>

    <!--更新实体信息，null值字段不更新-->
    <update id="updateIgnoreNull" parameterType="${cfg.baseDbBeanPackage}.${table.beanName}">
        UPDATE ${table.tableName}
        <set>
            <#list crud_columns as col>
            <#if col.columnName!='is_delete'>
                <if test="${col.propertyName} != null">
            `${col.columnName}` = <#noparse>#{</#noparse>${col.propertyName}<#noparse>}</#noparse>,
                </if>
            </#if>
            </#list>
        </set>
        WHERE <#list table.pkColumnList as pk><#if pk_index gt 0> AND</#if> ${pk} = <#noparse>#{</#noparse>${table.pkPropertyList[pk_index]}<#noparse>}</#noparse></#list>
        <#list columns as col>
            <#if col.columnName=='is_delete'>
            AND ${col.columnName} = 0
            </#if>
        </#list>
    </update>

    <!--根据主键删除实体-->
    <delete id="delete">
        UPDATE ${table.tableName} SET is_delete=1 WHERE <#list table.pkColumnList as pk><#if pk_index gt 0> AND</#if> ${pk} = <#noparse>#{</#noparse>${table.pkPropertyList[pk_index]}<#noparse>}</#noparse></#list>
    </delete>
    <!--分页查询-->
    <select id="listByPage" resultMap="beanMap">
        SELECT
        <include refid="columns"/>
        FROM
        ${table.tableName} a
        <#list columns as col>
            <#if col.columnName=='is_delete'>
                WHERE is_delete=0
            </#if>
        </#list>
        ORDER BY id DESC
    </select>
</mapper>