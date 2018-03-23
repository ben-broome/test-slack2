package com.icon.overrides.jsp.taglib.tophead.util;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.PrintWriter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.icon.groupattributes.model.GroupAttributes;
import com.icon.themecustomizations.service.ThemeCustomizationsCacheManagementLocalService;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.theme.ThemeDisplay;

public class TopHeadIncludeServiceTest {

	private TopHeadIncludeService topHeadIncludeService;

	@Mock
	private PrintWriter mockPrintWriter;

	@Mock
	private GroupAttributes mockGroupAttributes;

	@Mock
	private ThemeDisplay mockThemeDisplay;

	@Mock
	private Layout mockLayout;

	@Mock
	private LayoutSet mockLayoutSet;

	@Mock
	private ThemeCustomizationsCacheManagementLocalService mockThemeCustomizationsCacheManagementLocalService;

	@Before
	public void setUp() {
		initMocks(this);

		topHeadIncludeService = new TopHeadIncludeService();
		topHeadIncludeService.setThemeCustomizationsCacheManagementLocalService(mockThemeCustomizationsCacheManagementLocalService);
	}

	@Test
	public void addCustomStyles_WhenNoCustomStylesAreDefined_ThenNothingIsAddedToThePrintWriter() {
		long groupId = 123;
		when(mockThemeCustomizationsCacheManagementLocalService.getCachedGroupCustomStyles(groupId)).thenReturn(null);

		topHeadIncludeService.addCustomStyles(mockPrintWriter, groupId);

		verifyZeroInteractions(mockPrintWriter);
	}

	@Test
	public void addCustomStyles_WhenCustomStylesAreSpecified_ThenAddsCustomStylesToThePrintWriter() {
		long groupId = 123;
		String customStyles = "customStyles";
		when(mockThemeCustomizationsCacheManagementLocalService.getCachedGroupCustomStyles(groupId)).thenReturn(customStyles);

		topHeadIncludeService.addCustomStyles(mockPrintWriter, groupId);

		verify(mockPrintWriter, times(1)).println("<style type=\"text/css\" data-senna-track=\"temporary\">" + customStyles + "</style>");
	}

	@Test
	public void addGoogleFontFamilies_WhenGroupAttributesIsNull_ThenNothingIsAddedToThePrintWriter() {
		topHeadIncludeService.addGoogleFontFamilies(mockPrintWriter, null);

		verifyZeroInteractions(mockPrintWriter);
	}

	@Test
	public void addGoogleFontFamilies_WhenNoGoogleFontFamiliesAreDefined_ThenNothingIsAddedToThePrintWriter() {
		when(mockGroupAttributes.getGoogleFontFamilies()).thenReturn(null);

		topHeadIncludeService.addGoogleFontFamilies(mockPrintWriter, mockGroupAttributes);

		verifyZeroInteractions(mockPrintWriter);
	}

	@Test
	public void addGoogleFontFamilies_WhenGoogleFontFamiliesAreSpecified_ThenAddsGoogleFontFamiliesToThePrintWriter() {
		String googleFontFamilies = "googleFontFamilies";
		when(mockGroupAttributes.getGoogleFontFamilies()).thenReturn(googleFontFamilies);

		topHeadIncludeService.addGoogleFontFamilies(mockPrintWriter, mockGroupAttributes);

		verify(mockPrintWriter, times(1)).println("<link href='//fonts.googleapis.com/css?family=" + googleFontFamilies + "' rel='stylesheet' type='text/css'>");
	}

	@Test
	public void addMetaDescription_WhenGroupAttributesIsNull_ThenNothingIsAddedToThePrintWriter() {
		topHeadIncludeService.addMetaDescription(mockPrintWriter, null);

		verifyZeroInteractions(mockPrintWriter);
	}

	@Test
	public void addMetaDescription_WhenNoError_ThenAddsMetaDescriptionToThePrintWriter() {
		String metaDescription = "metaDescription";
		when(mockGroupAttributes.getMetaDescription()).thenReturn(metaDescription);

		topHeadIncludeService.addMetaDescription(mockPrintWriter, mockGroupAttributes);

		verify(mockPrintWriter, times(1)).println("<meta name='description' content='" + metaDescription + "'>");
	}

	@Test
	public void addMetaKeywords_WhenGroupAttributesIsNull_ThenNothingIsAddedToThePrintWriter() {
		topHeadIncludeService.addMetaKeywords(mockPrintWriter, null);

		verifyZeroInteractions(mockPrintWriter);
	}

	@Test
	public void addMetaKeywords_WhenNoError_ThenAddsMetaKeywordsToThePrintWriter() {
		String metaDescription = "metaDescription";
		when(mockGroupAttributes.getMetaKeywords()).thenReturn(metaDescription);

		topHeadIncludeService.addMetaKeywords(mockPrintWriter, mockGroupAttributes);

		verify(mockPrintWriter, times(1)).println("<meta name='keywords' content='" + metaDescription + "'>");
	}

	@Test
	public void addPageCss_WhenLayoutSetHasNoCss_ThenNothingIsAddedToThePrintWriter() {
		when(mockThemeDisplay.getLayoutSet()).thenReturn(mockLayoutSet);
		when(mockLayoutSet.getCss()).thenReturn(null);

		topHeadIncludeService.addPageCss(mockPrintWriter, mockThemeDisplay);

		verifyZeroInteractions(mockPrintWriter);
	}

	@Test
	public void addPageCss_WhenLayoutSetHasCss_ThenAddsLayoutSetCssToThePrintWriter() {
		String layoutSetCss = "layoutSetCss";
		when(mockThemeDisplay.getLayoutSet()).thenReturn(mockLayoutSet);
		when(mockLayoutSet.getCss()).thenReturn(layoutSetCss);

		topHeadIncludeService.addPageCss(mockPrintWriter, mockThemeDisplay);

		verify(mockPrintWriter, times(1)).println("<style type='text/css' data-senna-track='temporary'>" + layoutSetCss + "</style>");
	}
}
