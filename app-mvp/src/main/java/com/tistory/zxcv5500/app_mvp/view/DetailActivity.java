package com.tistory.zxcv5500.app_mvp.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.tistory.zxcv5500.app_mvp.R;
import com.tistory.zxcv5500.app_mvp.contract.DetailContract;
import com.tistory.zxcv5500.app_mvp.model.GitHubService;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {
	private static final String EXTRA_FULL_REPOSITORY_NAME = "EXTRA_FULL_REPOSITORY_NAME";
	private TextView fullNameTextView;
	private TextView detailTextView;
	private TextView repoStarTextView;
	private TextView repoForkTextView;
	private ImageView ownerImage;
	private DetailContract.UserActions detailPresenter;
	private String fullRepoName;

	public static void start(Context context, String fullRepositoryName) {
		final Intent intent = new Intent(context, DetailActivity.class);
		intent.putExtra(EXTRA_FULL_REPOSITORY_NAME, fullRepositoryName);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
	}

	@Override
	public String getFullRepositoryName() {
		return null;
	}

	@Override
	public void showRepositoryInfo(GitHubService.RepositoryItem reponse) {

	}

	@Override
	public void startBrowser(String url) {

	}

	@Override
	public void showError(String message) {

	}
}
