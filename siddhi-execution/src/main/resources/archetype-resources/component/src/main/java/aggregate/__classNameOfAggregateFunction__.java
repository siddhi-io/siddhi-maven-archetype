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
// for more information refer https://siddhi.io/en/v5.0/docs/query-guide/#aggregate-function
public class ${classNameOfAggregateFunction} extends AttributeAggregatorExecutor<State> {

    /**
     * The initialization method for AttributeAggregatorExecutor, which will be called before other
     * methods to validate all configurations and initiating variables.
     *
     * @param expressionExecutors          Executors of each attributes in the function,
     *                                     can be used on an event to get the attribute value
     * @param processingMode               Query processing mode
     * @param outputExpectsExpiredEvents   Whether expired events are to be sent as output
     * @param configReader                 this hold the {@link AttributeAggregatorExecutor} extensions
     *                                     configuration reader from which system parameters can ne read.
     * @param siddhiQueryContext           Siddhi query runtime context
     * @return StateFactory for the Function which contains logic for the updated state based on arrived events.
     */
    @Override
    protected StateFactory<State> init(ExpressionExecutor[] expressionExecutors, ProcessingMode processingMode,
                                        boolean outputExpectsExpiredEvents, ConfigReader configReader,
                                        SiddhiQueryContext siddhiQueryContext) {
        /*
        Siddhi state can keep any objects that needs to be updated based on arrival and removal of events.
        A state factory which can be used to create a State object needs to be returned. The state that is created
        at the core level will then be returned in processAdd() and processRemove() which then can be manipulated.
        State object will be specific to a function in this case it returns a Aggregate State.

        Can return null if state need not be kept for the function.
        */
        return new StateFactory<State>() {
            @Override
            public State createNewState() {
                return new AggregateState();
            }
        };
    }


    /**
     * Get attribute's type in the expressionExecutors.
     *
     * @return attribute's type
     */
    @Override
    public Attribute.Type getReturnType() {
        return null;
    }

    /**
     * The main execution method which will be called upon event arrival when there are zero or one Function parameter.
     *
     * @param  data     null if the Function parameter count is zero or
     *                  runtime data value of the Function parameter
     * @param state     current query state
     * @return the Function result
     */
    @Override
    public Object processAdd(Object data, State state) {
        return null;
    }

    /**
     * The main execution method which will be called upon event arrival
     * when there are more than one Function parameter.
     *
     * @param data      the runtime values of  parameters
     * @param state     current query state
     * @return the value
     */
    @Override
    public Object processAdd(Object[] data, State state) {
        return null;
    }

    /**
     * The main execution method which will be called upon event expired or out
     * when there are zero or one Function parameter.
     *
     * @param data      null if the Function parameter count is zero or
     *                  runtime data value of the Function parameter
     * @param state     current query state
     * @return the value
     */
    @Override
    public Object processRemove(Object data, State state) {
        return null;
    }

    /**
     * The main execution method which will be called upon event expired or out
     * when there are more than one Function parameter.
     *
     * @param  data     null if the Function parameter count is zero or
     *                  runtime data value of the Function parameter
     * @param state     current query state
     * @return the value
     */
    @Override
    public Object processRemove(Object[] data, State state) {
        return null;
    }

    /**
     * The execution method which will be called to reset the event.
     *
     * @param state     current query state
     * @return the value
     */
    @Override
    public Object reset(State state) {
        return null;
    }

    /**
     * Class used to hold any data that depends on arrival and remoal of events. The state will be stored periodically
     * if persistence is enabled, and restored in case of server startup after a crash.
     *
     * The class is function specific and can keep any variables.
     */
    class AggregateState extends State {

        /**
         * Indicates to Siddhi is state can be destroyed in the cleanup of states.
         *
         * @return whether state can be destroyed.
         */
        @Override
        public boolean canDestroy() {
            return false;
        }

        /**
         * Persists the returned map.
         *
         * @return Map of key value pairs which needs to be persisted
         */
        @Override
        public Map<String, Object> snapshot() {
            return null;
        }

        /**
         * Returns the latest persisted map (returned in snapshot method). This can be used to
         * restore the State(AggregateState)
         *
         * @param persistedMap latest map that was returned in the snapshot method.
         */
        @Override
        public void restore(Map<String, Object> persistedMap) {

        }
    }
}
