package org.jiangys.tool.idea

import com.intellij.DynamicBundle
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey

/**
 * DynamicBundle是IntelliJ IDEA插件开发中的一个类，用于实现插件的国际化（i18n）功能。它可以动态地加载和管理插件的本地化资源文件，使得插件可以根据用户的语言环境自动切换显示语言，从而提高插件的可用性和用户体验。
 *
 * 具体来说，DynamicBundle可以通过调用其提供的方法来获取插件的本地化字符串，例如：
 *
 * String message = DynamicBundle.message("my.plugin.message.key");
 * 其中，"my.plugin.message.key"是插件中定义的一个字符串键，对应着插件的一个本地化字符串。DynamicBundle会根据当前用户的语言环境自动加载并返回对应的本地化字符串，从而实现插件的国际化功能。
 *
 * 除了获取本地化字符串外，DynamicBundle还可以用于获取插件的本地化图标、颜色等资源，以及动态刷新本地化资源文件等功能。
 */
@NonNls
private const val BUNDLE = "messages.MyBundle"

object MyBundle : DynamicBundle(BUNDLE) {

    @Suppress("SpreadOperator")
    @JvmStatic
    fun message(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any) =
        getMessage(key, *params)

    @Suppress("SpreadOperator", "unused")
    @JvmStatic
    fun messagePointer(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any) =
        getLazyMessage(key, *params)
}
