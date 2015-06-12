<!--Created by: Kyle L Frisbie-->
<!--Date: 5/1/15-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>
    <xsl:template match="/">

        <html>
            <head>
                <title><h1>Apartments to Rent</h1></title>

                <body>
                    <!--Display data for every property in rental_listings-->
                    <xsl:for-each select="rental_listings/property">
                            <center>
                                <!-- Property Address-->
                                <h2>
                                    <xsl:value-of select="address/@number"/>
                                    <xsl:text> </xsl:text>
                                    <xsl:value-of select="address/@street"/>
                                    ,<xsl:text> </xsl:text>Unit:
                                    <xsl:text> </xsl:text>
                                    <xsl:value-of select="address/@unit"/>
                                    <xsl:text> </xsl:text>
                                    <xsl:value-of select="address/@city"/>
                                    <xsl:text> </xsl:text>
                                    <xsl:value-of select="address/@state"/>
                                    ,<xsl:text> </xsl:text>
                                    <xsl:value-of select="address/@zip"/>
                                </h2>
                            </center>

                        <!--Create table of property data-->
                            <h3>Features of this property:</h3>
                            <table border="3">
                                <tr>
                                    <!-- Table headers-->
                                    <th>Bedrooms</th>
                                    <th>Bathrooms</th>
                                    <th>SQ. FT.</th>
                                    <th>Parking Spots</th>
                                    <th>Pets allowed</th>
                                    <th>Washer/Drier included</th>
                                    <tr>
                                        <!--Data corresponding with table headers-->
                                        <td>
                                            <center>
                                                <xsl:value-of select="description/@nbeds"/>
                                            </center>
                                        </td>
                                        <td>
                                            <center>
                                                <xsl:value-of select="description/@nbaths"/>
                                            </center>
                                        </td>
                                        <td>
                                            <center>
                                                <xsl:value-of select="description/@sqft"/>
                                            </center>
                                        </td>
                                        <td>
                                            <center>
                                                <xsl:value-of select="description/@nparking_spots"/>
                                            </center>
                                        </td>
                                        <td>
                                            <center>
                                                <xsl:value-of select="description/@pet"/>
                                            </center>
                                        </td>
                                        <td>
                                            <center>
                                                <xsl:value-of select="description/@washer_drier"/>
                                            </center>
                                        </td>
                                    </tr>
                                </tr>
                            </table>

                            <p>
                                <!-- Monthly rent section-->
                                <b>Monthly Rent:</b><xsl:text> </xsl:text>
                                $<xsl:value-of select="rent"/>
                            </p>

                        <!-- Application process section, including an ordered list of process-->
                            <h3>The Application Process</h3>
                            <ol>
                                <xsl:for-each select="application_process/step">
                                    <li>
                                        <!-- Display root of each step as its own list item-->
                                        <xsl:value-of select="."/>
                                    </li>
                                </xsl:for-each>
                            </ol>

                        <!-- Optional comments area-->
                            <h3>Comments</h3>
                            <p>
                                <xsl:value-of select="comments"/>
                            </p>
                    </xsl:for-each>
                </body>
            </head>
        </html>
    </xsl:template>

</xsl:stylesheet>