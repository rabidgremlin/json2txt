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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import com.beust.jcommander.JCommander;

/**
 * Main class for utility.
 * 
 * @author Jonathan Ackerman
 * @version $Id$
 * 
 */
public class Json2Txt
{

  /**
   * Main method for the app. Parses command line options and kicks things off.
   * 
   * @param args The command lien args.
   */
  public static void main(String[] args)
  {
    // parse the command line args
    Args appArgs = new Args();
    JCommander commander = new JCommander(appArgs, args);

    // check that we got some paths to process
    if (appArgs.paths.size() == 0)
    {
      commander.usage();
      System.exit(1);
    }

    try
    {
      // setup input and output, reader and writer
      Reader input;
      Writer output;

      // was input file supplied?
      if (appArgs.inputFile != null)
      {
        // yep, read from file
        input = new FileReader(appArgs.inputFile);
      }
      else
      {
        // nope, use stdin
        input = new InputStreamReader(System.in);
      }

      // was output file specified ?
      if (appArgs.outputFile != null)
      {
        // yep, create file
        output = new FileWriter(appArgs.outputFile);
      }
      else
      {
        // nope, use stdout
        output = new OutputStreamWriter(System.out);
      }

      // create the processor
      Processor processor = new Processor(input, output, appArgs.paths);

      // start processing
      processor.process();
    }
    catch (Throwable t)
    {
      // oops, something went wrong, dump stacktrace and bail
      t.printStackTrace();
      System.exit(1);
    }
  }
}
