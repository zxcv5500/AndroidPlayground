package com.tistory.zxcv5500.app_mvp.contract;

import com.tistory.zxcv5500.app_mvp.model.GitHubService;

/**
 * Created by zxcv5500 on 2018-01-08.
 */

public interface DetailContract {
	/**
	 * MVP의 View가 구현할 인터페이스
	 * Presenter가 View를 조작할 때 이용한다.
	 */
	interface View {
		String getFullRepositoryName();

		void showRepositoryInfo(GitHubService.RepositoryItem reponse);

		void startBrowser(String url);

		void showError(String message);

	}

	/**
	 * MVP의 Presenter가 구현할 인터페이스
	 * View를 클릭했을 때 등 View가 Presenter에 알리기 위해 이용한다.
	 */
	interface UserActions {
		void titleClick();

		void prepare();
	}
}
