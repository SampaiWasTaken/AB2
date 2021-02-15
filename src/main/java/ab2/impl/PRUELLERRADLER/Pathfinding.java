package ab2.impl.PRUELLERRADLER;

import java.util.ArrayList;
import java.util.Set;

public class Pathfinding
{
//abccba
    public static void parse(String word, CFG CFG)
    {
        Set<String> rules = CFG.getRules();
        String[] _tokens = new String[2];
        _tokens[0] = word.substring(0, word.length()/2);
        _tokens[1] = word.substring(word.length()/2);

        for (int i = _tokens[0].length(); i > 0; i--)
        {
            for (int j = 0; j > _tokens[1].length(); j++)
            {
                for (String rule : rules)
                {

                }
            }
        }

        System.out.println(_tokens[0] + " " + _tokens[1]);
    }
}
