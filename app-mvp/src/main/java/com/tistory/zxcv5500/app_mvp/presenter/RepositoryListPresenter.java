package com.tistory.zxcv5500.app_mvp.presenter;

import android.text.format.DateFormat;

import com.tistory.zxcv5500.app_mvp.contract.RepositoryListContract;
import com.tistory.zxcv5500.app_mvp.model.GitHubService;

import java.util.Calendar;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by sharpen on 2018. 1. 6..
 */

public class RepositoryListPresenter implements RepositoryListContract.UserActions {
	private final RepositoryListContract.View repositoryListView;
	private final GitHubService gitHubService;

	public RepositoryListPresenter(RepositoryListContract.View repositoryListView, GitHubService gitHubService) {
		// 1 RepositoryListContract.View로서 멤버변수에 저장한다
		this.repositoryListView = repositoryListView;
		this.gitHubService = gitHubService;
	}


	@Override
	public void selectLanguage(String language) {
		loadRepositories();
	}

	@Override
	public void selectRepositoryItem(GitHubService.RepositoryItem item) {
		repositoryListView.startDetailActivity(item.full_name);
	}

	private void loadRepositories() {
		// 2 로딩 중이므로 진행 표시줄을 표시한다
		repositoryListView.showProgress();

		// 일주일 전 날짜 문자열 지금이 2016-10-27이면 2016-10-20이라는 문자열을 얻는다
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		String text = DateFormat.format("yyyy-MM-dd", calendar).toString();

		// 3 Retrofit을 이용해 서버에 접근한다

		// 지난 일주일간 만들어지고 언어가 language인 것을 쿼리로 전달한다
		Observable<GitHubService.Repositories> observable = gitHubService.listRepos("language:" + repositoryListView.getSelectedLanguage() + " " + "created:>" + text);
		//입출력(IO)용 스레드로 통신해 메인 스레드로 결과를 받아오게 한다
		observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<GitHubService.Repositories>() {
			@Override
			public void onNext(GitHubService.Repositories repositories) {
				// 4 로딩을 마쳤으므로 진행표시줄을 표시하지 않는다
				repositoryListView.hideProgress();
				// 가져온 아이템을 표시하기 위해, RecyclerView에 아이템을 설정하고 갱신한다
				repositoryListView.showRepositories(repositories);
			}



			@Override
			public void onError(Throwable e) {
				// 통신에 실패하면 호출된다
				// 여기서는 스낵바를 표시한다(아래에 표시되는 바
				repositoryListView.showError();
			}

			@Override
			public void onCompleted() {
				// 아무것도 하지 않는다.
			}
		});
	}


}
