/**
 * Json2Txt - Extract text from JSON using 'XPath'
 * 
 * Copyright (C) 2011 Air New Zealand
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import jsonij.json.JPath;
import jsonij.json.JSON;
import jsonij.json.Value;

/**
 * The main code for the app. This class takes lines of text from the input stream, parses them as JSON and then picks
 * out the
 * values specified in the paths. Results are written to the output stream separated by tabs.
 * 
 * @author Jonathan Ackerman
 * @version $Id$
 * 
 */
public class Processor
{
  /** The input. */
  private Reader inp;

  /** The output. */
  private Writer out;

  /** The list of paths to apply to each line in the input. */
  private List<String> paths;

  /**
   * The constructor.
   * 
   * @param inp The input reader.
   * @param out The output writer.
   * @param paths The list of paths to apply.
   */
  public Processor(Reader inp, Writer out, List<String> paths)
  {
    this.inp = inp;
    this.out = out;
    this.paths = paths;
  }

  /**
   * This method kciks of the processing.
   * 
   * @throws Exception Thrown if there is a problem.
   */
  public void process()
    throws Exception
  {
    // wrap input & output so we can read and write lines
    BufferedReader binp = new BufferedReader(inp);
    PrintWriter pout = new PrintWriter(out);

    // process each line from input
    String line;
    while ((line = binp.readLine()) != null)
    {
      // parse the line
      JSON json = JSON.parse(line);

      // apply each each path
      for (int loop = 0; loop < paths.size(); loop++)
      {
        JPath jPath = JPath.parse(paths.get(loop));
        Value[] values = jPath.evaluateAll(json);

        // dump out the values if we got any
        for (int valLoop = 0; valLoop < values.length; valLoop++)
        {
          if (values[valLoop] != null && !values[valLoop].isNull())
          {
            pout.write(values[valLoop].getString());
          }

          // not the last value ?
          if (valLoop < values.length - 1)
          {
            // yep, add a tab seperator
            pout.write("\t");
          }
        }

        // not the last path ?
        if (loop < paths.size() - 1)
        {
          // yep, add a tab seperator
          pout.write("\t");
        }
      }

      // end of data for this line
      pout.println();
    }

    // flush and close
    pout.flush();
    pout.close();
  }
}
