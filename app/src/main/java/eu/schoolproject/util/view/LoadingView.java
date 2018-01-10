package eu.schoolproject.util.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.schoolproject.R;

/**
 * Created by BP on 07/04/2017.
 */

public class LoadingView extends RelativeLayout {

    @BindView(R.id.loading_layout)
    RelativeLayout rlLoadingLayout;
    @BindView(R.id.loading_progress_text)
    TextView tvProgressText;
    @BindView(R.id.loading_image)
    ImageView imgLoading;
    @BindView(R.id.loading_btn_again)
    Button btnLoadingAgain;
    @BindView(R.id.loading_progress)
    ProgressBar progressBar;

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        inflate(getContext(), R.layout.view_loading, this);
        ButterKnife.bind(this);
    }

    public void showLoading() {
        rlLoadingLayout.setVisibility(VISIBLE);
    }

    public void showError(final @NonNull String message) {
        progressBar.setVisibility(INVISIBLE);

        rlLoadingLayout.setVisibility(VISIBLE);
        imgLoading.setVisibility(VISIBLE);

        setText(message);
    }

    public void showError(final @NonNull String message, final @NonNull OnAgainClickListener onAgainClickListener) {
        showError(message);

        btnLoadingAgain.setVisibility(VISIBLE);
        btnLoadingAgain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                onAgainClickListener.clicked();
            }
        });
    }

    public void dismiss() {
        rlLoadingLayout.setVisibility(GONE);

        imgLoading.setVisibility(INVISIBLE);
        btnLoadingAgain.setVisibility(INVISIBLE);
        progressBar.setVisibility(VISIBLE);

        setText("");
    }

    private void reset() {
        setText("");

        progressBar.setVisibility(VISIBLE);
        imgLoading.setVisibility(INVISIBLE);
        btnLoadingAgain.setVisibility(INVISIBLE);
    }

    public void setText(final @NonNull String message) {
        tvProgressText.setText(message);
    }

    public interface OnAgainClickListener {
        void clicked();
    }
}
