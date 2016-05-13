package cd.rule;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import cd.proto.RuleProto;

import com.google.protobuf.TextFormat;

/**
 * Created by chendong on 16/5/5.
 */
@Component
public class RuleManager {

    private List<RuleProto.PageRule> pageRules;

    @PostConstruct
    public void init() throws IOException {
        pageRules = new ArrayList<>();
        List<String> availableRules = new ArrayList<>();
        try (InputStream in = getClass()
                .getResourceAsStream("/available-rules")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                availableRules.add(line);
                System.out.println(line);
            }
        }
        for (String ruleName : availableRules) {
            String fileName = "/rules/" + ruleName;
            try (InputStream in = getClass()
                    .getResourceAsStream(fileName)) {
                InputStreamReader reader = new InputStreamReader(in, "ASCII");
                RuleProto.PageRule.Builder builder = RuleProto.PageRule.newBuilder();
                TextFormat.merge(reader, builder);
                pageRules.add(builder.build());
            }
        }
    }

    public RuleProto.PageRule getPageRule(String url) {
        for (RuleProto.PageRule rule : pageRules) {
            Pattern pattern = Pattern.compile(rule.getPageRegular());
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                return rule;
            }
        }
        return null;
    }
}
