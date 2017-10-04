package ${package}.${typeOfScript};

import org.wso2.siddhi.annotation.Example;
import org.wso2.siddhi.annotation.Extension;
import org.wso2.siddhi.core.function.Script;
import org.wso2.siddhi.core.util.config.ConfigReader;
import org.wso2.siddhi.query.api.definition.Attribute.Type;

/**
 * This is a sample class-level comment, explaining what the extension class does.
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
 *                                        (if parameter doesn't depend on each event then dynamic parameter is false.)",
 *                               optional= "true/false, defaultValue= if it is optional then assign a default value
 *                                          according to the type."),
 * {@literal @}Parameter(name = "The name of the second parameter",
 *                               description= "The description of the second parameter",
 *                               type =   "Supported parameter types.
 *                                         eg:{DataType.STRING, DataType.INT, DataType.LONG etc}",
 *                               dynamic= "false
 *                                        (if parameter doesn't depend on each event then dynamic parameter is false.)",
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
 * {@literal @}Example(syntax = "sample query with Script annotation that explain how extension use in Siddhi."
 *                              description =" The description of the given example's query."
 *                      ),
 * }
 * )
 * </code></pre>
 */

@Extension(
        name = "${typeOfScript}",
        namespace = "script",
        description = " ",
        examples = {
                @Example(
                        syntax = " ",
                        description = " "
                )
        }
)

// for more information refer https://wso2.github.io/siddhi/documentation/siddhi-4.0/#scripts

public class ${classNameOfScript} extends Script {
    /**
     * The initialization method for {@link Script}, which will be called before other methods and validate
     * the all configuration and getting the initial values.
     *
     * @param s               denotes the function name of the {@link Script}.
     * @param s1              denotes the function body of the {@link Script}
     * @param configReader    to read the Script related system configuration.
     */
    @Override
    public void init(String s, String s1, ConfigReader configReader) {
    }

    /**
     * Method to evaluate the function
     *
     * @param s                 denotes the function name of the {@link Script}.
     * @param objects           contains data of function which is used to evaluate the function.
     *
     * @return data after the evaluated.
     */
    @Override
    public Object eval(String s, Object[] objects) {
        return null;
    }

    /**
     * Get return type of the function .
     *
     * @return return type of the function.
     */
    @Override
    public Type getReturnType() {
        return null;
    }

    /**
     * set return type of the function.
     */
    @Override
    public void setReturnType(Type type) {
    }
}

