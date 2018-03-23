package com.icon.overrides.jsp.taglib.tophead;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.icon.groupattributes.model.GroupAttributes;
import com.icon.groupattributes.service.GroupAttributesLocalService;
import com.icon.overrides.jsp.taglib.tophead.util.TopHeadIncludeService;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

@Component(immediate = true, service = DynamicInclude.class)
public class TopHeadDynamicIncludePost extends BaseDynamicInclude {

	private GroupAttributesLocalService groupAttributesLocalService;
	private TopHeadIncludeService topHeadIncludeService;

	@Override
	public void include(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String key) throws IOException {
		ThemeDisplay themeDisplay = (ThemeDisplay) httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getScopeGroupId();
		GroupAttributes groupAttributes = groupAttributesLocalService.fetchGroupAttributes(groupId);
		PrintWriter writer = httpServletResponse.getWriter();

		topHeadIncludeService.addMetaKeywords(writer, groupAttributes);
		topHeadIncludeService.addMetaDescription(writer, groupAttributes);
		topHeadIncludeService.addGoogleFontFamilies(writer, groupAttributes);
		topHeadIncludeService.addCustomStyles(writer, groupId);
		topHeadIncludeService.addPageCss(writer, themeDisplay);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#post");
	}

	@Reference
	protected void setTopHeadIncludeService(TopHeadIncludeService topHeadIncludeService) {
		this.topHeadIncludeService = topHeadIncludeService;
	}

	@Reference
	protected void setGroupAttributesLocalService(GroupAttributesLocalService groupAttributesLocalService) {
		this.groupAttributesLocalService = groupAttributesLocalService;
	}

}
