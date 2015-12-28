package com.doomonafireball.betterpickers.datepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;

import com.doomonafireball.betterpickers.PickerLinearLayout;
import com.doomonafireball.betterpickers.R;
import com.doomonafireball.betterpickers.UnderlinePageIndicatorPicker;
import com.doomonafireball.betterpickers.ZeroTopPaddingTextView;

public class DateView extends PickerLinearLayout {

    private ZeroTopPaddingTextView mMonth;
    private ZeroTopPaddingTextView mDate;
    private ZeroTopPaddingTextView mYearLabel;
    private UnderlinePageIndicatorPicker mUnderlinePageIndicatorPicker;

    private ColorStateList mTitleColor;

    /**
     * Instantiate a DateView
     *
     * @param context the Context in which to inflate the View
     */
    public DateView(Context context) {
        this(context, null);
    }

    /**
     * Instantiate a DateView
     *
     * @param context the Context in which to inflate the View
     * @param attrs attributes that define the title color
     */
    public DateView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Init defaults
        mTitleColor = getResources().getColorStateList(R.color.dialog_text_color_holo_dark);

        setWillNotDraw(false);
    }

    /**
     * Set a theme and restyle the views. This View will change its title color.
     *
     * @param themeResId the resource ID for theming
     */
    public void setTheme(int themeResId) {
        if (themeResId != -1) {
            TypedArray a = getContext().obtainStyledAttributes(themeResId, R.styleable.BetterPickersDialogFragment);

            mTitleColor = a.getColorStateList(R.styleable.BetterPickersDialogFragment_bpTitleColor);
        }

        restyleViews();
    }

    private void restyleViews() {
        if (mMonth != null) {
            mMonth.setTextColor(mTitleColor);
        }
        if (mDate != null) {
            mDate.setTextColor(mTitleColor);
        }
        if (mYearLabel != null) {
            mYearLabel.setTextColor(mTitleColor);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mMonth = (ZeroTopPaddingTextView) findViewById(R.id.month);
        mDate = (ZeroTopPaddingTextView) findViewById(R.id.date);
        mYearLabel = (ZeroTopPaddingTextView) findViewById(R.id.year_label);
        // Reorder based on locale
        char[] dateFormatOrder = DateFormat.getDateFormatOrder(getContext());
        removeAllViews();
        for (int i = 0; i < dateFormatOrder.length; i++) {
            switch (dateFormatOrder[i]) {
                case DatePicker.DateFormat_DATE:
                    addView(mDate);
                    break;
                case DatePicker.DateFormat_MONTH:
                    addView(mMonth);
                    break;
                case DatePicker.DateFormat_YEAR:
                    addView(mYearLabel);
                    break;
            }
        }

        restyleViews();
    }

    /**
     * Set the date shown
     *
     * @param month a String representing the month of year
     * @param dayOfMonth an int representing the day of month
     * @param year an int representing the year
     */
    public void setDate(String month, int dayOfMonth, int year) {
        if (mMonth != null) {
            if (month.equals("")) {
                mMonth.setText("-");
                mMonth.setEnabled(false);
            } else {
                mMonth.setText(month);
                mMonth.setEnabled(true);
            }
        }
        if (mDate != null) {
            if (dayOfMonth <= 0) {
                mDate.setText("-");
                mDate.setEnabled(false);
            } else {
                mDate.setText(Integer.toString(dayOfMonth));
                mDate.setEnabled(true);
            }
        }
        if (mYearLabel != null) {
            if (year <= 0) {
                mYearLabel.setText("----");
                mYearLabel.setEnabled(false);
            } else {
                String yearString = Integer.toString(year);
                // Pad to 4 digits
                while (yearString.length() < 4) {
                    yearString = "-" + yearString;
                }
                mYearLabel.setText(yearString);
                mYearLabel.setEnabled(true);
            }
        }
    }

    /**
     * Allow attachment of the UnderlinePageIndicator
     *
     * @param indicator the indicator to attach
     */
    public void setUnderlinePage(UnderlinePageIndicatorPicker indicator) {
        mUnderlinePageIndicatorPicker = indicator;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mUnderlinePageIndicatorPicker.setTitleView(this);
    }

    /**
     * Set an onClickListener for notification
     *
     * @param mOnClickListener an OnClickListener from the parent
     */
    public void setOnClick(OnClickListener mOnClickListener) {
        mDate.setOnClickListener(mOnClickListener);
        mMonth.setOnClickListener(mOnClickListener);
        mYearLabel.setOnClickListener(mOnClickListener);
    }

    /**
     * Get the date TextView
     *
     * @return the date TextView
     */
    public ZeroTopPaddingTextView getDate() {
        return mDate;
    }

    /**
     * Get the month TextView
     *
     * @return the month TextView
     */
    public ZeroTopPaddingTextView getMonth() {
        return mMonth;
    }

    /**
     * Get the year TextView
     *
     * @return the year TextView
     */
    public ZeroTopPaddingTextView getYear() {
        return mYearLabel;
    }

    @Override
    public View getViewAt(int index) {
        return getChildAt(index);
    }
}
