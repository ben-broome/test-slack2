package com.icon.overrides.jsp.taglib.tophead;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import com.icon.groupattributes.model.GroupAttributes;
import com.icon.groupattributes.service.GroupAttributesLocalService;
import com.icon.overrides.jsp.taglib.tophead.util.TopHeadIncludeService;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude.DynamicIncludeRegistry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

@RunWith(PowerMockRunner.class)
public class TopHeadDynamicIncludePostTest {

	private static final String KEY = "key";
	private static final Long GROUP_ID = 123l;

	private TopHeadDynamicIncludePost topHeadDynamicIncludePost;

	@Mock
	private GroupAttributesLocalService mockGroupAttributesLocalService;

	@Mock
	private TopHeadIncludeService mockTopHeadIncludeService;

	@Mock
	private DynamicIncludeRegistry mockDynamicIncludeRegistry;

	@Mock
	private HttpServletRequest mockHttpServletRequest;

	@Mock
	private HttpServletResponse mockHttpServletResponse;

	@Mock
	private ThemeDisplay mockThemeDisplay;

	@Mock
	private PrintWriter mockPrintWriter;

	@Mock
	private GroupAttributes mockGroupAttributes;

	@Before
	public void setUp() {
		initMocks(this);

		topHeadDynamicIncludePost = new TopHeadDynamicIncludePost();
		topHeadDynamicIncludePost.setGroupAttributesLocalService(mockGroupAttributesLocalService);
		topHeadDynamicIncludePost.setTopHeadIncludeService(mockTopHeadIncludeService);
	}

	@Test
	public void include_WhenNoErrors_ThenAddsMetaKeywords() throws Exception {
		mockBasicDetails();

		topHeadDynamicIncludePost.include(mockHttpServletRequest, mockHttpServletResponse, KEY);

		verify(mockTopHeadIncludeService, times(1)).addMetaKeywords(mockPrintWriter, mockGroupAttributes);
	}

	@Test
	public void include_WhenNoErrors_ThenAddsMetaDescription() throws Exception {
		mockBasicDetails();

		topHeadDynamicIncludePost.include(mockHttpServletRequest, mockHttpServletResponse, KEY);

		verify(mockTopHeadIncludeService, times(1)).addMetaDescription(mockPrintWriter, mockGroupAttributes);
	}

	@Test
	public void include_WhenNoErrors_ThenAddsGoogleFontFamilies() throws Exception {
		mockBasicDetails();

		topHeadDynamicIncludePost.include(mockHttpServletRequest, mockHttpServletResponse, KEY);

		verify(mockTopHeadIncludeService, times(1)).addGoogleFontFamilies(mockPrintWriter, mockGroupAttributes);
	}

	@Test
	public void include_WhenNoErrors_ThenAddsCustomStyles() throws Exception {
		mockBasicDetails();

		topHeadDynamicIncludePost.include(mockHttpServletRequest, mockHttpServletResponse, KEY);

		verify(mockTopHeadIncludeService, times(1)).addCustomStyles(mockPrintWriter, GROUP_ID);
	}

	@Test
	public void include_WhenNoErrors_ThenAddsPageCss() throws Exception {
		mockBasicDetails();

		topHeadDynamicIncludePost.include(mockHttpServletRequest, mockHttpServletResponse, KEY);

		verify(mockTopHeadIncludeService, times(1)).addPageCss(mockPrintWriter, mockThemeDisplay);
	}

	@Test
	public void register_WhenNoErrors_ThenRegistersTheTopHeadPostPage() {

		topHeadDynamicIncludePost.register(mockDynamicIncludeRegistry);

		verify(mockDynamicIncludeRegistry, times(1)).register("/html/common/themes/top_head.jsp#post");

	}

	private void mockBasicDetails() throws IOException {
		when(mockHttpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)).thenReturn(mockThemeDisplay);
		when(mockThemeDisplay.getScopeGroupId()).thenReturn(GROUP_ID);
		when(mockGroupAttributesLocalService.fetchGroupAttributes(GROUP_ID)).thenReturn(mockGroupAttributes);
		when(mockHttpServletResponse.getWriter()).thenReturn(mockPrintWriter);
	}

}