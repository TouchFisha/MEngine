package touchfish.unit.util.script.generator;

import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Configuration;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;


/**
 *
 * 问号等于点
 *
 * ${value?capitalize} 全部单词的首字母大写
 * ${value?c_upper_case} 换为大写
 * ${value?c_lower_case} 换为小写
 *
 * 模板循环
 *
 * <#list ['a', 'b', 'c']>
 *   <ul>
 *    <#items as x>
 *      <li>${x?index}</li>
 *    </#items>
 *   </ul>
 * </#list>
 *
 *
 *
 *
 */

public class FrameTest {

    public static void main(String[] args) throws Exception {

        /* ------------------------------------------------------------------------ */
        /* You should do this ONLY ONCE in the whole application life-cycle:        */

        /* Create and adjust the configuration singleton */
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setDirectoryForTemplateLoading(new File("./Resource/Templates"));
        // Recommended settings for new projects:
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        cfg.setSQLDateAndTimeTimeZone(TimeZone.getDefault());

        /* ------------------------------------------------------------------------ */
        /* You usually do these for MULTIPLE TIMES in the application life-cycle:   */

        /* Create a data-model */
        Map root = new HashMap();
        root.put("user", "Big Joe");
        Product latest = new Product();
        latest.setUrl("products/greenmouse.html");
        latest.setName("green mouse");
        root.put("latestProduct", latest);

        /* Get the template (uses cache internally) */
        Template temp = cfg.getTemplate("test.ftlh");

        /* Merge data-model with template */
        Writer out = new OutputStreamWriter(System.out);
        temp.process(root, out);
        // Note: Depending on what `out` is, you may need to call `out.close()`.
        // This is usually the case for file output, but not for servlet output.

        out.close();

    }


}

