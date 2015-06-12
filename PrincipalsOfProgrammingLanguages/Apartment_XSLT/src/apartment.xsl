<stylesheet version="1.0" xmlns="http://www.w3.org/1999/XSL/Transform" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <output method="html"/>
    <template match="/">

        <html>
            <head>
                <title>Apartments to Rent</title>
            </head>

            <body>
                
                <xsl:for-each select="rental_listings/property"
            </body>
        </html>
    </template>

</stylesheet>