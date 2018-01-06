package com.tistory.zxcv5500.app_mvp.contract;

/**
 * Created by sharpen on 2018. 1. 6..
 */

public interface RepositoryListContract {

	interface View {
		String getSelectedLanguage();
		void showProgress();
		void hideProgress();
		void showRepositories(GitHubService.Repositories repositories);
		void showError();
		void startDetailActivity(String fullRepositoryName);
	}


}
