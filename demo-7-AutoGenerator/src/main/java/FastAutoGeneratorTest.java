import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class FastAutoGeneratorTest {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/mybatisplus-quick-start?characterEncoding=utf-8&userSSL=false", "root", "admin123").globalConfig(builder -> {
                    builder.author("wangming") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("/Users/maizi/1.git-project/7.日常学习项目/V2/MyBatisPlus-Quick-Start/demo-7-AutoGenerator/src/main/java"); // 指定输出目录
                }).packageConfig(builder -> {
                    builder.parent("com.maizi") // 设置父包名
                            .moduleName("demo") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "/Users/maizi/1.git-project/7.日常学习项目/V2/MyBatisPlus-Quick-Start/demo-7-AutoGenerator/src" +
                                    "/main/java/com/maizi/demo/mapper/xml/")); // 设置mapperXml生成路径
                }).strategyConfig(builder -> {
                    builder.addInclude("t2_user") // 设置需要生成的表名
                            .addTablePrefix("t2_", "t1_"); // 设置过滤表前缀
                }).templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}