package ${package}.${storeTypeInLowerCase};

import org.wso2.siddhi.annotation.Example;
import org.wso2.siddhi.annotation.Extension;
import org.wso2.siddhi.core.exception.ConnectionUnavailableException;
import org.wso2.siddhi.core.table.record.AbstractRecordTable;
import org.wso2.siddhi.core.table.record.ExpressionBuilder;
import org.wso2.siddhi.core.table.record.RecordIterator;
import org.wso2.siddhi.core.util.collection.operator.CompiledCondition;
import org.wso2.siddhi.core.util.collection.operator.CompiledExpression;
import org.wso2.siddhi.core.util.config.ConfigReader;
import org.wso2.siddhi.query.api.definition.TableDefinition;

import java.util.List;
import java.util.Map;

/**
 * This is a sample class-level comment, explaining what the Sink extension class does.
 */

/**
 * Annotation of Siddhi Extension.
 * <pre><code>
 * eg:-
 * {@literal @}Extension(
 * name = "The name of the extension",
 * namespace = "The namespace of the extension",
 * description = "The description of the extension (optional).",
 * //Sink configurations
 * parameters = {
 * {@literal @}Parameter(name = "The name of the first parameter",
 *                               description= "The description of the first parameter",
 *                               type =  "Supported parameter types.
 *                                        eg:{DataType.STRING, DataType.INT, DataType.LONG etc}",
 *                               dynamic= "false
 *                                         (if parameter doesn't depend on each event then dynamic parameter is false.
 *                                         In Source, only use static parameter)",
 *                               optional= "true/false, defaultValue= if it is optional then assign a default value
 *                                          according to the type."),
 * {@literal @}Parameter(name = "The name of the second parameter",
 *                               description= "The description of the second parameter",
 *                               type =   "Supported parameter types.
 *                                         eg:{DataType.STRING, DataType.INT, DataType.LONG etc}",
 *                               dynamic= "false
 *                                         (if parameter doesn't depend on each event then dynamic parameter is false.
 *                                         In Source, only use static parameter)",
 *                               optional= "true/false, defaultValue= if it is optional then assign a default value
 *                                         according to the type."),
 * },
 * //If Source system configurations will need then
 * systemParameters = {
 * {@literal @}SystemParameter(name = "The name of the first  system parameter",
 *                                      description="The description of the first system parameter." ,
 *                                      defaultValue = "the default value of the system parameter.",
 *                                      possibleParameter="the possible value of the system parameter.",
 *                               ),
 * },
 * examples = {
 * {@literal @}Example(syntax = "sample query that explain how extension use in Siddhi."
 *                              description =" The description of the given example's query."
 *                      ),
 * }
 * )
 * </code></pre>
 */

@Extension(
        name = "${storeTypeInLowerCase}",
        namespace = "store",
        description = " ",
        parameters = {
               /* @Parameter(
                        name = " ",
                        description = " " ,
                        dynamic = false/true,
                        optional = true/false, defaultValue = " ",
                        type = {DataType.INT, DataType.BOOL, DataType.STRING, DataType.DOUBLE, }
                        ),*/
        },
        systemParameter = {
               /*@SystemParameter(
                        name = " ",
                        description = " ",
                        defaultValue = " ",
                        possibleParameters = " "),*/
        },

        examples = {
                @Example(
                        syntax = " ",
                        description = " "
                )
        }
)

// for more information refer https://wso2.github.io/siddhi/documentation/siddhi-4.0/#event-table-types

public class ${className} extends AbstractRecordTable {

     /**
      * Initializing the Record Table
      *
      * @param tableDefinition definintion of the table with annotations if any
      * @param configReader this hold the {@link AbstractRecordTable} configuration reader.
      */
     @Override
     protected void init(TableDefinition tableDefinition, ConfigReader configReader) {}

     /**
     * Add records to the Table
     *
     * @param records records that need to be added to the table, each Object[] represent a record and it will match
     *                the attributes of the Table Definition.
     */
     @Override
     protected void add(List<Object[]> records) throws ConnectionUnavailableException {}

     /**
     * Find records matching the compiled condition
     *
     * @param findConditionParameterMap map of matching StreamVariable Ids and their values corresponding to the
     *                                  compiled condition
     * @param compiledCondition         the compiledCondition against which records should be matched
     * @return                          RecordIterator of matching records
     */
     @Override
     protected RecordIterator<Object[]> find(Map<String, Object> findConditionParameterMap,
            CompiledCondition compiledCondition) throws ConnectionUnavailableException {
            return null;
            }

      /**
      * Check if matching record exist or not
      *
      * @param containsConditionParameterMap map of matching StreamVariable Ids and their values corresponding to the
      *                                      compiled condition
      * @param compiledCondition             the compiledCondition against which records should be matched
      * @return if matching record found or not
      */
     @Override
     protected boolean contains(Map<String, Object> containsConditionParameterMap,
            CompiledCondition compiledCondition) throws ConnectionUnavailableException {
            return false;
            }

     /**
     * Delete all matching records
     *
     * @param deleteConditionParameterMaps    map of matching StreamVariable Ids and their values corresponding to the
     *                                        compiled condition
     * @param compiledCondition               the compiledCondition against which records should be matched for deletion
     * @throws ConnectionUnavailableException if end point is unavailable the ConnectionUnavailableException thrown
     *                                        such that the  system will take care retrying for connection
      **/
     @Override
     protected void delete(List<Map<String, Object>> deleteConditionParameterMaps, CompiledCondition compiledCondition)
            throws ConnectionUnavailableException {

            }

     /**
     * Update all matching records
     *
     * @param compiledCondition               the compiledCondition against which records should be matched for update
     * @param list                            map of matching StreamVariable Ids and their values corresponding to the
     *                                        compiled condition based on which the records will be updated
     * @param map                             the attributes and values that should be updated if the condition matches
     * @param list1                           the attributes and values that should be updated for the matching records
     * @throws ConnectionUnavailableException if end point is unavailable the ConnectionUnavailableException thrown
     *                                        such that the  system will take care retrying for connection
     */
     @Override
     protected void update(CompiledCondition compiledCondition, List<Map<String, Object>> list,
            Map<String, CompiledExpression> map, List<Map<String, Object>> list1)
            throws ConnectionUnavailableException {

            }

     /**
     * Try updating the records if they exist else add the records
     *
     * @param list                            map of matching StreamVariable Ids and their values corresponding to the
     *                                        compiled condition based on which the records will be updated
     * @param compiledCondition               the compiledCondition against which records should be matched for update
     * @param map                             the attributes and values that should be updated if the condition matches
     * @param list1                           the values for adding new records if the update condition did not match
     * @param list2                           the attributes and values that should be updated for the matching records
     * @throws ConnectionUnavailableException if end point is unavailable the ConnectionUnavailableException thrown
     *                                        such that the  system will take care retrying for connection
     */
     @Override
     protected void updateOrAdd(CompiledCondition compiledCondition, List<Map<String, Object>> list,
            Map<String, CompiledExpression> map, List<Map<String, Object>> list1,
            List<Object[]> list2) throws ConnectionUnavailableException {

            }

     /**
     * Compile the matching condition
     *
     * @param expressionBuilder that helps visiting the conditions in order to compile the condition
     * @return compiled condition that can be used for matching events in find, contains, delete, update and
     * updateOrAdd
     */
     @Override
     protected CompiledCondition compileCondition(ExpressionBuilder expressionBuilder) {
            return null;
            }

     /**
     * Compile the matching condition
     *
     * @param expressionBuilder   that helps visiting the conditions in order to compile the condition
     * @return compiled condition that can be used for matching events in find, contains, delete, update and
     *                            updateOrAdd
     */
     @Override
     protected CompiledExpression compileSetAttribute(ExpressionBuilder expressionBuilder) {
            return null;
            }

     /**
     * This method will be called before the processing method.
     * Intention to establish connection to publish event.
     * @throws ConnectionUnavailableException if end point is unavailable the ConnectionUnavailableException thrown
     *                                        such that the  system will take care retrying for connection
     */
     @Override
     protected void connect() throws ConnectionUnavailableException {}

     /**
     * Called after all publishing is done, or when {@link ConnectionUnavailableException} is thrown
     * Implementation of this method should contain the steps needed to disconnect.
     */
     @Override
     protected void disconnect() {}

     /**
     * The method can be called when removing an event receiver.
     * The cleanups that have to be done after removing the receiver could be done here.
     */
     @Override
     protected void destroy() {}
}

