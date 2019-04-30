package ${package}.aggregate;

import io.siddhi.annotation.Example;
import io.siddhi.annotation.Extension;
import io.siddhi.annotation.ReturnAttribute;
import io.siddhi.annotation.util.DataType;
import io.siddhi.core.config.SiddhiQueryContext;
import io.siddhi.core.executor.ExpressionExecutor;
import io.siddhi.core.query.processor.ProcessingMode;
import io.siddhi.core.query.selector.attribute.aggregator.AttributeAggregatorExecutor;
import io.siddhi.core.util.config.ConfigReader;
import io.siddhi.core.util.snapshot.state.State;
import io.siddhi.core.util.snapshot.state.StateFactory;
import io.siddhi.query.api.definition.Attribute;


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
 * returnAttributes = {
 * {@literal @}ReturnAttribute(
 *                          description = "Description of the returned attribute from the function",
 *                          type =   "Supported parameter types.
 *                                    eg:{DataType.STRING, DataType.INT, DataType.LONG etc}" ),
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
        name = "${_nameOfFunction.toLowerCase()}",
        namespace = "${_nameSpaceOfFunction.toLowerCase()}",
        description = " ",
        parameters = {
                /*@Parameter(name = " ",
                        description = " " ,
                        dynamic = false/true,
                        optional = true/false, defaultValue = " ",
                        type = {DataType.INT, DataType.BOOL, DataType.STRING, DataType.DOUBLE, }),
                        type = {DataType.INT, DataType.BOOL, DataType.STRING, DataType.DOUBLE, }),*/
        },
        returnAttributes = {
                @ReturnAttribute(
                        description = " ",
                        type = DataType.OBJECT
                )
        },
        examples = {
                @Example(
                        syntax = " ",
                        description = " "
                )
        }
)
// for more information refer
//                      https://siddhi-io.github.io/siddhi/documentation/siddhi-5.x/query-guide-5.x/#aggregate-function
public class ${classNameOfAggregateFunction} extends AttributeAggregatorExecutor<State> {

    /**
     * Get attribute's type in the expressionExecutors .
     *
     * @return attribute's type
     */
    @Override
    public Attribute.Type getReturnType() {
        return null;
    }

    /**
     * The main execution method which will be called upon event arrival
     * when there are zero or one Function parameter
     *
     * @param  data null if the Function parameter count is zero or
     *             runtime data value of the Function parameter
     * @return the Function result
     */
    @Override
    public Object processAdd(Object data, State state) {
        return null;
    }

    /**
     * The main execution method which will be called upon event arrival
     * when there are more than one Function parameter
     *
     * @param  data the runtime values of  parameters
     * @return the value
     */
    @Override
    public Object processAdd(Object[] data, State state) {
        return null;
    }

    /**
     * The main execution method which will be called upon event expired or out
     *when there are zero or one Function parameter
     *
     * @param data null if the Function parameter count is zero or
     *             runtime data value of the Function parameter
     * @return the value
     */
    @Override
    public Object processRemove(Object data, State state) {
        return null;
    }

    /**
     * The main execution method which will be called upon event expired or out
     * when there are more than one Function parameter
     *
     * @param  data null if the Function parameter count is zero or
     *             runtime data value of the Function parameter
     * @return the value
     */
    @Override
    public Object processRemove(Object[] data, State state) {
        return null;
    }

    /**
     * The initialization method for {@link AttributeAggregatorExecutor<State>}, which will be called before other
     * methods and validate the all configuration and getting the initial values.
     * @param configReader        this hold the {@link AttributeAggregatorExecutor<State>} extensions configuration
     *                            reader.
     */
    @Override
    protected StateFactory<State> init(ExpressionExecutor[] expressionExecutors, ProcessingMode processingMode,
                                       boolean b, ConfigReader configReader, SiddhiQueryContext siddhiQueryContext) {
        return null;
    }

    /**
     * The execution method which will be called to reset the event
     *
     * @return the value
     */
    @Override
    public Object reset(State state) {
        return null;
    }
}
