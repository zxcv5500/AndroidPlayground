package com.tistory.zxcv5500.app_mvp.view;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.tistory.zxcv5500.app_mvp.R;
import com.tistory.zxcv5500.app_mvp.contract.RepositoryListContract;
import com.tistory.zxcv5500.app_mvp.model.GitHubService;
import com.tistory.zxcv5500.app_mvp.presenter.RepositoryListPresenter;

public class RepositoryListActivity extends AppCompatActivity implements RepositoryAdapter.OnRepositoryItemClickListener, RepositoryListContract.View {
	private RepositoryListContract.UserActions repositoryListPresenter;

	private Spinner languageSpinner;
	private ProgressBar progressBar;
	private CoordinatorLayout coordinatorLayout;

	private RepositoryAdapter repositoryAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repository_list);

		// View를 설정
		setupView();

		// Presenter의 인스턴스를 생성
		final GitHubService gitHubService = ((NewGitHubReposApplication) getApplication()).getGitHubService();
		repositoryListPresenter = new RepositoryListPresenter((RepositoryListContract.View) this, gitHubService);
	}

	private void setupView() {

		// Spinner
		languageSpinner = (Spinner) findViewById(R.id.language_spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapter.addAll("java", "objective-c", "swift", "groovy", "python", "ruby", "c");
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		languageSpinner.setAdapter(adapter);
		languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
				// 스피너의 선택 내용이 바뀌면 호출된다.
				String lanuage = (String) languageSpinner.getItemAtPosition(position);
				// Presenter에 프로그래밍 언어를 선택했다고 알려준다
				repositoryListPresenter.selectLanguage(lanuage);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});
	}

	/**
	 * RecyclerView에 클릭됐다.
	 */
	@Override
	public void onRepositoryItemClick(GitHubService.RepositoryItem item) {
		repositoryListPresenter.selectRepositoryItem(item);
	}

	// ========= RepositoryListContract.View 구현 ==============
	// 이 곳에서 Presenter으로 부터 지시를 받아 View의 변경 등을 한다.

	@Override
	public String getSelectedLanguage() {
		return null;
	}

	@Override
	public void showProgress() {
		progressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideProgress() {
		progressBar.setVisibility(View.GONE);
	}

	@Override
	public void showRepositories(GitHubService.Repositories repositories) {
		// 3 리포지토리 목록을 Adapter에 설정한다.
		repositoryAdapter.setItemsAndRefresh(repositories.items);
	}

	@Override
	public void showError() {
		Snackbar.make(coordinatorLayout, "읽을 수 없습니다.", Snackbar.LENGTH_LONG)
				.setAction("Action", null).show();
	}

	@Override
	public void startDetailActivity(String fullRepositoryName) {

	}
}
