package ${package}.streamfunction;

import io.siddhi.annotation.Example;
import io.siddhi.annotation.Extension;
import io.siddhi.core.config.SiddhiQueryContext;
import io.siddhi.core.executor.ExpressionExecutor;
import io.siddhi.core.query.processor.stream.function.StreamFunctionProcessor;
import io.siddhi.core.util.config.ConfigReader;
import io.siddhi.core.util.snapshot.state.State;
import io.siddhi.core.util.snapshot.state.StateFactory;
import io.siddhi.query.api.definition.AbstractDefinition;
import io.siddhi.query.api.definition.Attribute;

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
        examples = {
                @Example(
                        syntax = " ",
                        description = " "
                )
        }
)
//For more information, see https://siddhi.io/en/v5.1/docs/query-guide/#stream-function
public class ${classNameOfStreamFunction} extends StreamFunctionProcessor<State> {

    /**
     * The initialization method for {@link StreamFunctionProcessor}, which will be called before other
     * methods to validate all configurations and initiating variables.
     *
     * @param inputDefinition              the incoming stream definition
     * @param attributeExpressionExecutors the executors of each function parameters,
     *                                     can be used on an event to get the parameter value
     * @param configReader                 this hold the {@link StreamFunctionProcessor} extensions
     *                                     configuration reader from which system parameters can ne read.
     * @param outputExpectsExpiredEvents   is expired events sent as output
     * @param siddhiQueryContext           Siddhi query runtime context
     * @return StateFactory for the Function which contains logic for the updated state based on arrived events.
     */
    @Override
    protected StateFactory<State> init(AbstractDefinition inputDefinition,
                                        ExpressionExecutor[] attributeExpressionExecutors, ConfigReader configReader,
                                        boolean outputExpectsExpiredEvents, SiddhiQueryContext siddhiQueryContext) {
        /*
        Siddhi state can keep any objects that needs to be updated based on arrival and removal of events.
        A state factory which can be used to create a State object needs to be returned. The state that is created
        at the core level will then be returned in processAdd() and processRemove() which then can be manipulated.
        State object will be specific to a function in this case it returns a StreamFunction State.

        Can return null if state need not be kept for the function.
        */
        return new StateFactory<State>() {
            @Override
            public State createNewState() {
                return new StreamFunctionState();
            }
        };
    }

    /** The method should return the output's additional attributes list introduced by the function.
     *
     * @return List of additional attributes from the function
     */
    @Override
    public List<Attribute> getReturnAttributes() {
        return null;
    }

    /**
     * This will be called only once and this can be used to acquire required resources for the processing element.
     * This will be called after initializing the system and before starting to process the events.
     */
    @Override
    public void start() {
    }

    /**
     * The process method used when zero or one function parameter is provided.
     *
     * @param data null if the function parameter count is zero or runtime data value of the function parameter
     * @return the data for additional output attribute introduced by the function
     */
    @Override
    protected Object[] process(Object data) {
        return new Object[0];
    }

    /**
     * The process method used when more than one function parameters are provided.
     *
     * @param data the data values for the function parameters
     * @return the data for additional output attributes introduced by the function
     */
    @Override
    protected Object[] process(Object[] data) {
        return new Object[0];
    }

    /**
     * This will be called only once and this can be used to release the acquired resources for processing.
     * This will be called before shutting down the system.
     */
    @Override
    public void stop() {
    }

    /**
     * Class used to hold any data that depends on arrival and remoal of events. The state will be stored periodically
     * if persistence is enabled, and restored in case of server startup after a crash.
     *
     * The class is function specific and can keep any variables.
     */
    class StreamFunctionState extends State {

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
         * restore the State(StreamFunctionState)
         *
         * @param persistedMap latest map that was returned in the snapshot method.
         */
        @Override
        public void restore(Map<String, Object> persistedMap) {

        }
    }
}
