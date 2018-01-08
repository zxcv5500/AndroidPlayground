package com.tistory.zxcv5500.app_mvp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.tistory.zxcv5500.app_mvp.R;
import com.tistory.zxcv5500.app_mvp.contract.DetailContract;
import com.tistory.zxcv5500.app_mvp.model.GitHubService;
import com.tistory.zxcv5500.app_mvp.presenter.DetailPresenter;

import org.w3c.dom.Text;

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
		final Intent intent = getIntent();
		fullRepoName = intent.getStringExtra(EXTRA_FULL_REPOSITORY_NAME);

		fullNameTextView = (TextView) DetailActivity.this.findViewById(R.id.fullname);
		detailTextView = (TextView) findViewById(R.id.detail);
		repoStarTextView = (TextView) findViewById(R.id.repo_star);
		repoForkTextView = (TextView) findViewById(R.id.repo_fork);
		ownerImage = (ImageView) findViewById(R.id.owner_image);

		final GitHubService gitHubService = ((NewGitHubReposApplication) getApplication()).getGitHubService();
		detailPresenter = new DetailPresenter((DetailContract.View) this, gitHubService);
		detailPresenter.prepare();
	}

	@Override
	public String getFullRepositoryName() {
		return fullRepoName;
	}

	@Override
	public void showRepositoryInfo(GitHubService.RepositoryItem reponse) {
		fullNameTextView.setText(reponse.full_name);
		detailTextView.setText(reponse.description);
		repoStarTextView.setText(reponse.stargazers_count);
		repoForkTextView.setText(reponse.forks_count);

		// 서버에서 이미지를 가져와 imageView에 넣는다.
		Glide.with(DetailActivity.this)
				.load(reponse.owner.avatar_url)
				.asBitmap().centerCrop().into(new BitmapImageViewTarget(ownerImage) {
			@Override
			protected void setResource(Bitmap resource) {
				RoundedBitmapDrawable circularBitmapDrawable =
						RoundedBitmapDrawableFactory.create(getResources(), resource);
				circularBitmapDrawable.setCircular(true);
				ownerImage.setImageDrawable(circularBitmapDrawable);

			}
		});

		// 로고와 리포지토리 이름을 탭하면, 제작자의 Github 페이지를 브라우저로 연다.
		View.OnClickListener listener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				detailPresenter.titleClick();
			}
		};
		fullNameTextView.setOnClickListener(listener);
		ownerImage.setOnClickListener(listener);
	}

	@Override
	public void startBrowser(String url) {
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
	}

	@Override
	public void showError(String message) {
		Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
				.show();
	}
}
