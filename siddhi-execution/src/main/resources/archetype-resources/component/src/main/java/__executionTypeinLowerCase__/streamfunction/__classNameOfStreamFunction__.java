package ${package}.${executionTypeinLowerCase}.streamfunction;

import org.wso2.siddhi.annotation.Example;
import org.wso2.siddhi.annotation.Extension;
import org.wso2.siddhi.core.config.SiddhiAppContext;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.query.processor.stream.function.StreamFunctionProcessor;
import org.wso2.siddhi.core.util.config.ConfigReader;
import org.wso2.siddhi.query.api.definition.AbstractDefinition;
import org.wso2.siddhi.query.api.definition.Attribute;

import java.util.List;
import java.util.Map;

/**
 * This is a sample class-level comment, explaining what the extension class does.
 */

/**
 * Annotation of Siddhi Extension.
 * <pre><code>
 * eg:-
 * {@literal @}Extension(
 * name = "The name of the extension",
 * namespace = "The namespace of the extension, that is used to group multiple extensions",
 * description = "The description of the extension (optional).",
 * //Execution configurations
 * parameters = {
 * {@literal @}Parameter(name = "The name of the first parameter",
 *                               description= "The description of the first parameter",
 *                               type =  "Supported parameter types.
 *                                        eg:{DataType.STRING, DataType.INT, DataType.LONG etc}",
 *                               dynamic= "false
 *                                         (if parameter doesn't depend on each event then dynamic parameter is false.
 *                                         )",
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
        name = "${executionTypeinLowerCase}",
        namespace = "",
        description = " ",
        parameters = {
                /*@Parameter(name = " ",
                        description = " " ,
                        dynamic = false/true,
                        optional = true/false, defaultValue = " ",
                        type = {DataType.INT, DataType.BOOL, DataType.STRING, DataType.DOUBLE, }),
                        type = {DataType.INT, DataType.BOOL, DataType.STRING, DataType.DOUBLE, }),*/
        },
        examples = {
                @Example(
                        syntax = " ",
                        description = " "
                )
        }
)

public class ${classNameOfStreamFunction} extends StreamFunctionProcessor {

    /**
     * The process method used when more than one function parameters are provided
     *
     * @param data the data values for the function parameters
     * @return the data for additional output attributes introduced by the function
     */
    @Override
    protected Object[] process(Object[] data) {
        return new Object[0];
    }

    /**
     * The process method used when zero or one function parameter is provided
     *
     * @param data null if the function parameter count is zero or runtime data value of the function parameter
     * @return the data for additional output attribute introduced by the function
     */
    @Override
    protected Object[] process(Object data) {
        return new Object[0];
    }

    /**
     * The initialization method for {@link StreamFunctionProcessor},
     * which will be called before other methods and validate
     * the all configuration and getting the initial values.
     * @param inputDefinition               the incoming stream definition
     * @param attributeExpressionExecutors     the executors of each function parameters
     * @param configReader                     this hold the {@link StreamFunctionProcessor} extensions
     *                                         configuration reader.
     * @param siddhiAppContext                 the context of the siddhi app
     * @return the output's additional attributes list introduced by the function
     */
    @Override
    protected List<Attribute> init(AbstractDefinition inputDefinition,
                                   ExpressionExecutor[] attributeExpressionExecutors, ConfigReader configReader,
                                   SiddhiAppContext siddhiAppContext) {
        return null;
    }

    /**
     * This will be called only once and this can be used to acquire
     * required resources for the processing element.
     * This will be called after initializing the system and before
     * starting to process the events.
     */
    @Override
    public void start() {

    }

    /**
     * This will be called only once and this can be used to release
     * the acquired resources for processing.
     * This will be called before shutting down the system.
     */
    @Override
    public void stop() {

    }

    /**
     * Used to collect the serializable state of the processing element, that need to be
     * persisted for reconstructing the element to the same state on a different point of time
     *
     * @return stateful objects of the processing element as an map
     */
    @Override
    public Map<String, Object> currentState() {
        return null;
    }

    /**
     * Used to restore serialized state of the processing element, for reconstructing
     * the element to the same state as if was on a previous point of time.
     *
     * @param state the stateful objects of the processing element as a map.
     *              This is the same map that is created upon calling currentState() method.
     */
    @Override
    public void restoreState(Map<String, Object> state) {

    }
}
