package ${package}.window;

import io.siddhi.annotation.Example;
import io.siddhi.annotation.Extension;
import io.siddhi.core.config.SiddhiQueryContext;
import io.siddhi.core.event.ComplexEventChunk;
import io.siddhi.core.event.state.StateEvent;
import io.siddhi.core.event.stream.MetaStreamEvent;
import io.siddhi.core.event.stream.StreamEvent;
import io.siddhi.core.event.stream.StreamEventCloner;
import io.siddhi.core.event.stream.holder.StreamEventClonerHolder;
import io.siddhi.core.event.stream.populater.ComplexEventPopulater;
import io.siddhi.core.executor.ExpressionExecutor;
import io.siddhi.core.executor.VariableExpressionExecutor;
import io.siddhi.core.query.processor.ProcessingMode;
import io.siddhi.core.query.processor.Processor;
import io.siddhi.core.query.processor.stream.AbstractStreamProcessor;
import io.siddhi.core.query.processor.stream.window.FindableProcessor;
import io.siddhi.core.query.processor.stream.window.WindowProcessor;
import io.siddhi.core.table.Table;
import io.siddhi.core.util.collection.operator.CompiledCondition;
import io.siddhi.core.util.collection.operator.MatchingMetaInfoHolder;
import io.siddhi.core.util.config.ConfigReader;
import io.siddhi.core.util.snapshot.state.State;
import io.siddhi.core.util.snapshot.state.StateFactory;
import io.siddhi.query.api.definition.AbstractDefinition;
import io.siddhi.query.api.expression.Expression;

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
 *                                       (if parameter doesn't depend on each event then dynamic parameter is false.)",
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
// For more information refer https://siddhi.io/en/v5.0/docs/query-guide/#window
public class ${classNameOfWindow} extends WindowProcessor<State> implements FindableProcessor {

    /**
     * The init method of the {@link WindowProcessor}, this method will be called before other methods.
     *
     * @param metaStreamEvent              the stream event meta
     * @param abstractDefinition           the incoming stream definition
     * @param expressionExecutors          the executors of each function parameters
     * @param configReader                 this hold the {@link AbstractStreamProcessor} extensions configuration
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
     * To construct a finder having the capability of finding events at the processor that corresponds to the incoming
     * matchingEvent and the given matching expression logic.
     *
     * @param expression                   the matching condition
     * @param matchingMetaInfoHolder      the meta structure of the incoming matchingEvent
     * @param variableExpressionExecutors the list of variable ExpressionExecutors already created
     * @param tableMap                    map of event tables
     * @param siddhiQueryContext          current siddhi query context
     * @return compiled Condition having the capability of matching events against the incoming matchingEvent
     */
    @Override
    public CompiledCondition compileCondition(Expression expression, MatchingMetaInfoHolder matchingMetaInfoHolder,
        List<VariableExpressionExecutor> variableExpressionExecutors,
        Map<String, Table> tableMap, SiddhiQueryContext siddhiQueryContext) {
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
     * @param state                 current state of the processor
     */
    @Override
    protected void processEventChunk(ComplexEventChunk complexEventChunk, Processor processor,
                                     StreamEventCloner streamEventCloner, ComplexEventPopulater
                                             complexEventPopulater, State state) {

    }

    /**
     * To find events from the processor event pool, that the matches the matchingEvent based on finder logic.
     *
     * @param matchingEvent     the event to be matched with the events at the processor
     * @param compiledCondition the execution element responsible for matching the corresponding events that matches
     *                          the matchingEvent based on pool of events at Processor
     * @return the matched events
     */
    @Override
    public StreamEvent find(StateEvent matchingEvent, CompiledCondition compiledCondition) {
        return null;
    }


    /**
     * This will be called only once and this can be used to release the acquired resources for processing.
     * This will be called before shutting down the system.
     */
    @Override
    public void stop() {

    }
}
