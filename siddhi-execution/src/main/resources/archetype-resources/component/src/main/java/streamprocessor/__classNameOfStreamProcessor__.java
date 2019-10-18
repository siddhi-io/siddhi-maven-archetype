package ${package}.streamprocessor;

import io.siddhi.annotation.Example;
import io.siddhi.annotation.Extension;
import io.siddhi.core.config.SiddhiQueryContext;
import io.siddhi.core.event.ComplexEventChunk;
import io.siddhi.core.event.stream.MetaStreamEvent;
import io.siddhi.core.event.stream.StreamEventCloner;
import io.siddhi.core.event.stream.holder.StreamEventClonerHolder;
import io.siddhi.core.event.stream.populater.ComplexEventPopulater;
import io.siddhi.core.executor.ExpressionExecutor;
import io.siddhi.core.query.processor.ProcessingMode;
import io.siddhi.core.query.processor.Processor;
import io.siddhi.core.query.processor.stream.StreamProcessor;
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
//For more information, see https://siddhi.io/en/v5.1/docs/query-guide/#stream-processor
public class ${classNameOfStreamProcessor}  extends StreamProcessor<State> {

    /**
     * The initialization method for {@link StreamProcessor}, which will be called before other methods and validate
     * the all configuration and getting the initial values.
     *
     * @param metaStreamEvent              the stream event meta
     * @param abstractDefinition           the incoming stream definition
     * @param expressionExecutors          the executors of each function parameters,
     *                                     can be used on an event to get the parameter value
     * @param configReader                 this hold the {@link StreamProcessor} extensions
     *                                     configuration reader from which system parameters can ne read.
     * @param streamEventClonerHolder      stream event cloner holder
     * @param outputExpectsExpiredEvents   is expired events sent as output
     * @param findToBeExecuted             find will be executed
     * @param siddhiQueryContext           current siddhi query context
     * @return StateFactory for the Function which contains logic for the updated state based on arrived events.
     */
    @Override
    protected StateFactory<State> init(MetaStreamEvent metaStreamEvent, AbstractDefinition abstractDefinition,
            ExpressionExecutor[] expressionExecutors, ConfigReader configReader,
            StreamEventClonerHolder streamEventClonerHolder, boolean outputExpectsExpiredEvents,
            boolean findToBeExecuted, SiddhiQueryContext siddhiQueryContext) {
        /*
        Siddhi state can keep any objects that needs to be updated based on arrival and removal of events.
        A state factory which can be used to create a State object needs to be returned. The state that is created
        at the core level will then be returned in processAdd() and processRemove() which then can be manipulated.
        State object will be specific to a function in this case it returns a StreamProcessor State.

        Can return null if state need not be kept for the function.
        */
        return new StateFactory<State>() {
            @Override
            public State createNewState() {
                return new StreamProcessorState();
            }
        };
    }

    /**
     * The method should return the output's additional attributes list introduced by the function.
     *
     * @return List of additional attributes from the function
     */
    @Override
    public List<Attribute> getReturnAttributes() {
        return null;
        }

    /**
     * Defines the behaviour of the processing, will be called after the init.
     *
     * @return ProcessingMode processing mode of the processor
     */
    @Override
    public ProcessingMode getProcessingMode() {
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
     * The main processing method that will be called upon event arrival.
     *
     * @param complexEventChunk     the event chunk that need to be processed
     * @param processor             the next processor to which the success events need to be passed
     * @param streamEventCloner     helps to clone the incoming event for local storage or modification
     * @param complexEventPopulater helps to populate the events with the resultant attributes
     * @param state                 current processor state
     */
    @Override
    protected void process(ComplexEventChunk complexEventChunk, Processor processor,
        StreamEventCloner streamEventCloner, ComplexEventPopulater complexEventPopulater,
        State state) {
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
    class StreamProcessorState extends State {

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
         * restore the State(StreamProcessorState)
         *
         * @param persistedMap latest map that was returned in the snapshot method.
         */
        @Override
        public void restore(Map<String, Object> persistedMap) {

        }
    }
}
