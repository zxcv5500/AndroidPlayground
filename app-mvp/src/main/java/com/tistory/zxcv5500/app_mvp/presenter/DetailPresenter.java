package com.tistory.zxcv5500.app_mvp.presenter;

import com.tistory.zxcv5500.app_mvp.contract.DetailContract;
import com.tistory.zxcv5500.app_mvp.model.GitHubService;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zxcv5500 on 2018-01-08.
 */

public class DetailPresenter implements DetailContract.UserActions {
	final DetailContract.View detailView;
	private final GitHubService gitHubService;
	private GitHubService.RepositoryItem repositoryItem;

	public DetailPresenter(DetailContract.View detailView, GitHubService gitHubService) {
		this.detailView = detailView;
		this.gitHubService = gitHubService;
	}

	@Override
	public void prepare() {
		loadRepositories();
	}

	private void loadRepositories() {
		String fullRepoName = detailView.getFullRepositoryName();

		// 리포지토리 이름을 /로 분할한다.
		final String[] repoData = fullRepoName.split("/");
		final String owner = repoData[0];
		final String repoName = repoData[1];
		gitHubService
			.detailRepo(owner, repoName)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Action1<GitHubService.RepositoryItem>() {
				@Override
				public void call(GitHubService.RepositoryItem reponse) {
					repositoryItem = reponse;
					detailView.showRepositoryInfo(reponse);

				}
			}, new Action1<Throwable>() {

				@Override
				public void call(Throwable throwable) {
					detailView.showError("읽을 수 없습니다.");
				}
			});
	}

	@Override
	public void titleClick() {
		try {
			detailView.startBrowser(repositoryItem.html_url);
		} catch (Exception e) {
			detailView.showError("링크를 열 수 없습니다.");
		}
	}
}
