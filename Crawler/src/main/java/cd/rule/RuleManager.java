package cd.rule;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cd.proto.RuleProto;

import com.google.protobuf.TextFormat;

/**
 * Created by chendong on 16/5/5.
 */
public class RuleManager {

    private List<RuleProto.PageRule> pageRules;

    public void init() throws IOException {
        pageRules = new ArrayList<>();
        try (InputStream in = getClass()
                .getResourceAsStream("/rules/youtube-detail.rule")) {
            InputStreamReader reader = new InputStreamReader(in, "ASCII");
            RuleProto.PageRule.Builder builder = RuleProto.PageRule.newBuilder();
            TextFormat.merge(reader, builder);
            pageRules.add(builder.build());
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
