package org.jiangys.tool.idea.utils

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.jiangys.tool.idea.PluginException

object JsonUtil {

    private val mapper = ObjectMapper()

    init {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

    fun toSimpleString(text: String): String {
        try {
            val node = mapper.readTree(text)
            return node.toString()
        } catch (e: JsonProcessingException) {
            throw PluginException("不是Json格式", e)
        }
    }

    fun toPrettyString(text: String): String {
        val node = mapper.readTree(text)
        return node.toPrettyString()
    }
}
