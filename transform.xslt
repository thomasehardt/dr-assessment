<?xml version="1.0"?>
<!--
	This can be used to verify that the XML output can be used to re-create the
	input.

	Best way to validate is to use XMLStarlet (http://xmlstar.sourceforge.net/):
	xmlstarlet tr transform.xslt output_file.xml

	the md5sum should match the input; for example, the output of

	$ xmlstarlet tr transform.xslt FirstRequirementOutput.xml | md5sum

	and

	$ cat src/main/resouces/nl_data.txt | md5sum

	should match
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output omit-xml-declaration="yes" indent="no" method="text"/>
	<xsl:strip-space elements="*"/>
	<xsl:preserve-space elements="value"/>
	<xsl:template match="sentenceComponent/value">
		<xsl:value-of select="./text()"/>
	</xsl:template>
</xsl:stylesheet>
