<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
    <html xmlns="http://www.w3.org/1999/xhtml">
    <body>
        <h2>Math Courses Reed</h2>
            <table border="1">
                <tr style="font-weighted:bold">
                    <th>Reg_num</th>
                    <th>Subj</th>
                    <th>crse</th>
                    <th>sect</th>
                    <th>title</th>
                    <th>units</th>
                    <th>Instructor</th>
                    <th>days</th>
                    <th>time</th>
                    <th>place</th>
                </tr>
                <xsl:for-each select="//course[subj='MATH']">
                    <tr>
                        <td><xsl:value-of select="reg_num " /></td>
                        <td><xsl:value-of select="subj " /></td>
                        <td><xsl:value-of select="crse "/> </td>
                        <td><xsl:value-of select="sect "/></td>
                        <td><xsl:value-of select="title "/></td>
                        <td><xsl:value-of select="units"/> </td>
                        <td><xsl:value-of select="instructor"/> </td>
                        <td><xsl:value-of select="days"/> </td>
                        <td>
                            <xsl:value-of select="time/start_time "/>
                            <xsl:value-of select="time/end_time"/>
                        </td>
                        <td>
                            <xsl:value-of select="place/building"/>
                            <xsl:value-of select="place/room"/>
                        </td>
                    </tr>
                </xsl:for-each>
            </table>
        </body>
    </html>
</xsl:template>
</xsl:stylesheet>