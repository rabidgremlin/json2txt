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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

/**
 * Helper class for jCommander to parse command line options.
 * 
 * @author Jonathan Ackerman
 * @version $Id$
 * 
 */
public class Args
{
  @Parameter(names = "-i", description = "Input file", converter = FileConverter.class)
  public File inputFile;

  @Parameter(names = "-o", description = "Output file", converter = FileConverter.class)
  public File outputFile;

  @Parameter(description = "JSON paths")
  public List<String> paths = new ArrayList<String>();
}
