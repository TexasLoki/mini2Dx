/**
 * Copyright (c) 2015, mini2Dx Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of the mini2Dx nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.mini2Dx.core.data;

import org.mini2Dx.core.data.annotation.Field;

/**
 * Common interface for reading/writing game data, e.g. game saves, preferences,
 * etc.
 * 
 * The location to save data in is automatically determined by the game
 * identifier and the current platform. All files/directories specified by the
 * developer will be automatically placed inside the pre-determined data
 * location.
 * 
 * Note: Objects must use {@link Field} annotations to be
 * serialized/deserialized properly
 */
public interface Data {
    /**
     * Converts XML from a file into an object. Note the object must use the
     * mini2Dx data annotations.
     * 
     * @param clazz
     *            The object type to convert the XML into
     * @param filepath
     *            The path to the XML file. This will be resolved as a path
     *            within the game data location.
     * @return The resulting object
     * @throws Exception
     *             Thrown if the XML is invalid, the file does not exist or the
     *             game data location cannot be accessed.
     */
    public <T> T readXml(Class<T> clazz, String... filepath) throws Exception;

    /**
     * Writes an object as XML to a file. Note the object must use the mini2Dx
     * data annotations.
     * 
     * @param object
     *            The object to be written to the file
     * @param filepath
     *            The path to the XML file. This will be resolved as a path
     *            within the game data location.
     * @throws Exception
     *             Thrown if the game data location cannot be accessed or the
     *             data cannot be written to the file.
     */
    public <T> void writeXml(T object, String... filepath) throws Exception;

    /**
     * Converts JSON from a file into an object. Note the object must use the
     * mini2Dx data annotations.
     * 
     * @param clazz
     *            The object type to convert the JSON into
     * @param filepath
     *            The path to the JSON file. This will be resolved as a path
     *            within the game data location.
     * @return The resulting object
     * @throws Exception
     *             Thrown if the XML is invalid, the file does not exist or the
     *             game data location cannot be accessed.
     */
    public <T> T readJson(Class<T> clazz, String... filepath) throws Exception;

    /**
     * Writes an object as JSON to a file. Note the object must use the mini2Dx
     * data annotations.
     * 
     * @param object
     *            The object to be written to the file
     * @param filepath
     *            The path to the JSON file. This will be resolved as a path
     *            within the game data location.
     * @throws Exception
     *             Thrown if the game data location cannot be accessed or the
     *             data cannot be written to the file.
     */
    public <T> void writeJson(T object, String... filepath) throws Exception;

    /**
     * Checks if the file exists in the game data location
     * 
     * @param filepath
     *            The path to the file within the game data location
     * @return True if the file exists
     * @throws Exception
     *             Thrown if the game data location cannot be accessed
     */
    public boolean hasFile(String... filepath) throws Exception;

    /**
     * Checks if the directory exists in the game data location
     * 
     * @param path
     *            The path to the directory within the game data location
     * @return True if the file exists
     * @throws Exception
     *             Thrown if the game data location cannot be accessed
     */
    public boolean hasDirectory(String... path) throws Exception;

    /**
     * Creates a directory within in the game data location
     * 
     * @param path
     *            The path to the directory within the game data location
     * @throws Exception
     *             Thrown if the game data location cannot be accessed or the
     *             directory could not be created
     */
    public void createDirectory(String... path) throws Exception;

    /**
     * Wipes all data within the game data location
     * 
     * @throws Exception
     *             Thrown if the game data location cannot be accessed or the
     *             data could not be wiped
     */
    public void wipe() throws Exception;
}
