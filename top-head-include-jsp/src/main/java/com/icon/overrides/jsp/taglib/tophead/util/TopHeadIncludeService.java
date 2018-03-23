package com.icon.overrides.jsp.taglib.tophead.util;

import java.io.PrintWriter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.icon.groupattributes.model.GroupAttributes;
import com.icon.themecustomizations.service.ThemeCustomizationsCacheManagementLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;

@Component(immediate = true, properties = {}, service = TopHeadIncludeService.class)
public class TopHeadIncludeService {

	private ThemeCustomizationsCacheManagementLocalService themeCustomizationsCacheManagementLocalService;

	public void addCustomStyles(PrintWriter writer, long groupId) {
		String groupCachedCustomStyles = themeCustomizationsCacheManagementLocalService.getCachedGroupCustomStyles(groupId);
		if (Validator.isNotNull(groupCachedCustomStyles)) {
			writer.println(String.format("<style type=\"text/css\" data-senna-track=\"temporary\">%s</style>", groupCachedCustomStyles));
		}
	}

	public void addGoogleFontFamilies(PrintWriter writer, GroupAttributes groupAttributes) {
		if (Validator.isNotNull(groupAttributes)) {
			String googleFontFamilies = groupAttributes.getGoogleFontFamilies();
			if (Validator.isNotNull(googleFontFamilies)) {
				writer.println(String.format("<link href='//fonts.googleapis.com/css?family=%s' rel='stylesheet' type='text/css'>", googleFontFamilies));
			}
		}
	}

	public void addMetaDescription(PrintWriter writer, GroupAttributes groupAttributes) {
		if (Validator.isNotNull(groupAttributes)) {
			writer.println(String.format("<meta name='description' content='%s'>", groupAttributes.getMetaDescription()));
		}
	}

	public void addMetaKeywords(PrintWriter writer, GroupAttributes groupAttributes) {
		if (Validator.isNotNull(groupAttributes)) {
			writer.println(String.format("<meta name='keywords' content='%s'>", groupAttributes.getMetaKeywords()));
		}
	}

	public void addPageCss(PrintWriter writer, ThemeDisplay themeDisplay) {
		String layoutSetCss = themeDisplay.getLayoutSet().getCss();
		if (Validator.isNotNull(layoutSetCss)) {
			writer.println(String.format("<style type='text/css' data-senna-track='temporary'>%s</style>", layoutSetCss));
		}
	}

	@Reference
	protected void setThemeCustomizationsCacheManagementLocalService(ThemeCustomizationsCacheManagementLocalService themeCustomizationsCacheManagementLocalService) {
		this.themeCustomizationsCacheManagementLocalService = themeCustomizationsCacheManagementLocalService;
	}

}
