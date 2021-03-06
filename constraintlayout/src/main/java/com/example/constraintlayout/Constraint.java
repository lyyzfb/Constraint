package com.example.constraintlayout;

/**
 * Created by LiuJin on 2018-03-28:6:55
 */

import android.graphics.Rect;
import android.view.View;

import java.util.Locale;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 约束,用于对布局中的view进行约束布局
 *
 * @author wuxio
 */
@SuppressWarnings("UnusedReturnValue")
public class Constraint {

    private static final String TAG = "Constraint";

    /**
     * 约束后的尺寸
     */
    int left;
    int top;
    int right;
    int bottom;

    /**
     * 水平/竖直方向有剩余空间时,偏移比
     */
    float horizontalBias = 0f;
    float verticalBias   = 0f;

    /**
     * 3位表示left是否有约束,1为有,0为没有
     * 2位表示top是否有约束,1为有,0为没有
     * 1位表示right是否有约束,1为有,0为没有
     * 0位表示bottom是否有约束,1为有,0为没有
     */
    private int stateFlags;

    /**
     * 一个支持约束的布局
     */
    private ConstraintSupport mParent;


    @Override
    public String toString() {

        return "Constraint{" +
                "left=" + left +
                ", top=" + top +
                ", right=" + right +
                ", bottom=" + bottom +
                ", horizontalBias=" + horizontalBias +
                ", verticalBias=" + verticalBias +
                '}';
    }


    /**
     * 不要自己创建,使用{@link ConstraintLayout#obtainConstraint()}获取空白约束
     *
     * @param parent 实现{@link ConstraintSupport}接口的布局
     */
    public Constraint(ConstraintSupport parent) {

        this.mParent = parent;
    }


    /**
     * 重置状态
     */
    public void init() {

        left = 0;
        top = 0;
        right = mParent.getParentRight();
        bottom = mParent.getParentBottom();

        horizontalBias = 0f;
        verticalBias = 0f;

        stateFlags = 0;
    }


    /**
     * 使用一个Rect 初始化
     */
    public void init(Rect rect) {

        left = rect.left;
        top = rect.top;
        right = rect.right;
        bottom = rect.bottom;

        horizontalBias = 0f;
        verticalBias = 0f;
    }


    /**
     * 使用一个位置 初始化
     */
    public void init(int left, int top, int right, int bottom) {

        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;

        horizontalBias = 0f;
        verticalBias = 0f;
    }

    //============================偏移============================


    /**
     * 设置水平偏移量
     *
     * @param horizontalBias 水平偏移量
     */
    public Constraint setHorizontalBias(float horizontalBias) {

        final int min = 0;
        final int max = 1;

        if (horizontalBias < min) {
            horizontalBias = min;
        }

        if (horizontalBias > max) {
            horizontalBias = max;
        }

        this.horizontalBias = horizontalBias;
        return this;
    }


    /**
     * 设置垂直偏移量
     *
     * @param verticalBias 垂直偏移量
     */
    public Constraint setVerticalBias(float verticalBias) {

        final int min = 0;
        final int max = 1;

        if (verticalBias < min) {
            verticalBias = min;
        }

        if (verticalBias > max) {
            verticalBias = max;
        }

        this.verticalBias = verticalBias;
        return this;
    }

    //============================ 标记受约束的边 ============================


    private void setLeftConstraint() {

        stateFlags |= 0b1;
    }


    private void setTopConstraint() {

        stateFlags |= 0b10;
    }


    private void setRightConstraint() {

        stateFlags |= 0b100;
    }


    private void setBottomConstraint() {

        stateFlags |= 0b1000;
    }


    /**
     * @return true: 以left为基准,layout
     */
    boolean isLeftConstraint() {

        return (stateFlags & 0b1) == 1;
    }


    /**
     * @return true: 以top为基准,layout
     */
    boolean isTopConstraint() {

        return (stateFlags >> 1 & 0b1) == 1;
    }


    /**
     * @return true: 以right为基准,layout
     */
    boolean isRightConstraint() {

        return (stateFlags >> 2 & 0b1) == 1;
    }


    /**
     * @return true: 以bottom为基准,layout
     */
    boolean isBottomConstraint() {

        return (stateFlags >> 3 & 0b1) == 1;
    }

    //============================约束至Parent============================


    /**
     * 约束自己的左边至布局的左边
     *
     * @param offset 偏移量,与坐标轴同向
     */
    public Constraint leftToLeftOfParent(int offset) {

        setLeftConstraint();
        left = mParent.getParentLeft() + offset;
        return this;
    }


    /**
     * 约束自己的左边至布局的左边,并指定宽度
     *
     * @param offset 偏移量,与坐标轴同向
     * @param width  宽度
     */
    public Constraint leftToLeftOfParent(int offset, int width) {

        setLeftConstraint();
        left = mParent.getParentLeft() + offset;
        right = left + width;
        return this;
    }


    /**
     * 约束自己的左边至布局的右边
     *
     * @param offset 偏移量,与坐标轴同向
     */
    public Constraint leftToRightOfParent(int offset) {

        checkParentRight();

        setLeftConstraint();
        left = mParent.getParentRight() + offset;
        return this;
    }


    /**
     * 约束自己的左边至布局的右边,并指定宽度
     *
     * @param offset 偏移量,与坐标轴同向
     * @param width  期望的宽度
     */
    public Constraint leftToRightOfParent(int offset, int width) {

        checkParentRight();

        setLeftConstraint();
        left = mParent.getParentRight() + offset;
        right = left + width;
        return this;
    }


    /**
     * 约束自己的右边至布局的左边
     *
     * @param offset 偏移量,与坐标轴同向
     */
    public Constraint rightToLeftOfParent(int offset) {

        setRightConstraint();
        right = mParent.getParentLeft() + offset;
        return this;
    }


    /**
     * 约束自己的右边至布局的左边,并指定宽度
     *
     * @param offset 偏移量,与坐标轴同向
     * @param width  期望的宽度
     */
    public Constraint rightToLeftOfParent(int offset, int width) {

        setRightConstraint();
        right = mParent.getParentLeft() + offset;
        left = right - width;
        return this;
    }


    /**
     * 约束自己的右边至布局的右边
     *
     * @param offset 偏移量,与坐标轴同向
     */
    public Constraint rightToRightOfParent(int offset) {

        checkParentRight();

        setRightConstraint();
        right = mParent.getParentRight() + offset;
        return this;
    }


    /**
     * 约束自己的右边至布局的右边,并指定宽度
     *
     * @param offset 偏移量,与坐标轴同向
     * @param width  期望的宽度
     */
    public Constraint rightToRightOfParent(int offset, int width) {

        checkParentRight();

        setRightConstraint();
        right = mParent.getParentRight() + offset;
        left = right - width;
        return this;
    }


    /**
     * 约束自己的上边至布局的上边
     *
     * @param offset 偏移量,与坐标轴同向
     */
    public Constraint topToTopOfParent(int offset) {

        setTopConstraint();
        top = mParent.getParentTop() + offset;
        return this;
    }


    /**
     * 约束自己的上边至布局的上边,并指定宽度
     *
     * @param offset 偏移量,与坐标轴同向
     * @param height 期望view的高度
     */
    public Constraint topToTopOfParent(int offset, int height) {

        setTopConstraint();
        top = mParent.getParentTop() + offset;
        bottom = top + height;
        return this;
    }


    /**
     * 约束自己的上边至布局的下边
     *
     * @param offset 偏移量,与坐标轴同向
     */
    public Constraint topToBottomOfParent(int offset) {

        checkParentBottom();

        setTopConstraint();
        top = mParent.getParentBottom() + offset;
        return this;
    }


    /**
     * 约束自己的上边至布局的下边,并指定高度
     *
     * @param offset 偏移量,与坐标轴同向
     * @param height 期望的高度
     */
    public Constraint topToBottomOfParent(int offset, int height) {

        checkParentBottom();

        setTopConstraint();
        top = mParent.getParentBottom() + offset;
        bottom = top + height;
        return this;
    }


    /**
     * 约束自己的下边至布局的上边
     *
     * @param offset 偏移量,与坐标轴同向
     */
    public Constraint bottomToTopOfParent(int offset) {

        setBottomConstraint();
        bottom = mParent.getParentTop() + offset;
        return this;
    }


    /**
     * 约束自己的下边至布局的上边,并指定高度
     *
     * @param offset 偏移量,与坐标轴同向
     * @param height 期望的高度
     */
    public Constraint bottomToTopOfParent(int offset, int height) {

        setBottomConstraint();
        bottom = mParent.getParentTop() + offset;
        top = bottom - height;
        return this;
    }


    /**
     * 约束自己的下边至布局的下边
     *
     * @param offset 偏移量,与坐标轴同向
     */
    public Constraint bottomToBottomOfParent(int offset) {

        checkParentBottom();

        setBottomConstraint();
        bottom = mParent.getParentBottom() + offset;
        return this;
    }


    /**
     * 约束自己的下边至布局的下边,并指定高度
     *
     * @param offset 偏移量,与坐标轴同向
     * @param height 期望的高度
     */
    public Constraint bottomToBottomOfParent(int offset, int height) {

        checkParentBottom();

        setBottomConstraint();
        bottom = mParent.getParentBottom() + offset;
        top = bottom - height;
        return this;
    }

    //============================约束至view============================


    /**
     * 约束自己的左边至位于position view的左边
     *
     * @param position view的position
     * @param offset   偏移量,与坐标轴同向
     */
    public Constraint leftToLeftOfView(int position, int offset) {

        setLeftConstraint();
        left = mParent.getViewLeft(position) + offset;
        return this;
    }


    /**
     * 约束自己的左边至位于position view的左边
     *
     * @param position view的position
     * @param offset   偏移量,与坐标轴同向
     * @param width    期望的宽度
     */
    public Constraint leftToLeftOfView(int position, int offset, int width) {

        setLeftConstraint();
        left = mParent.getViewLeft(position) + offset;
        right = left + width;
        return this;
    }


    /**
     * 约束自己的左边至位于position View的右边
     *
     * @param position view的position
     * @param offset   偏移量,与坐标轴同向
     */
    public Constraint leftToRightOfView(int position, int offset) {

        setLeftConstraint();
        left = mParent.getViewRight(position) + offset;
        return this;
    }


    /**
     * 约束自己的左边至位于position View的右边
     *
     * @param position view的position
     * @param offset   偏移量,与坐标轴同向
     * @param width    期望的宽度
     */
    public Constraint leftToRightOfView(int position, int offset, int width) {

        setLeftConstraint();
        left = mParent.getViewRight(position) + offset;
        right = left + width;
        return this;
    }


    /**
     * 约束自己的右边至位于position View的左边
     *
     * @param position view的position
     * @param offset   偏移量,与坐标轴同向
     */
    public Constraint rightToLeftOfView(int position, int offset) {

        setRightConstraint();
        right = mParent.getViewLeft(position) + offset;
        return this;
    }


    /**
     * 约束自己的右边至位于position View的左边
     *
     * @param position view的position
     * @param offset   偏移量,与坐标轴同向
     * @param width    期望的宽度
     */
    public Constraint rightToLeftOfView(int position, int offset, int width) {

        setRightConstraint();
        right = mParent.getViewLeft(position) + offset;
        left = right - width;
        return this;
    }


    /**
     * 约束自己的右边至位于position View的右边
     *
     * @param position view的position
     * @param offset   偏移量,与坐标轴同向
     */
    public Constraint rightToRightOfView(int position, int offset) {

        setRightConstraint();
        right = mParent.getViewRight(position) + offset;
        return this;
    }


    /**
     * 约束自己的右边至位于position View的右边
     *
     * @param position view的position
     * @param offset   偏移量,与坐标轴同向
     * @param width    期望的宽度
     */
    public Constraint rightToRightOfView(int position, int offset, int width) {

        setRightConstraint();
        right = mParent.getViewRight(position) + offset;
        left = right - width;
        return this;
    }


    /**
     * 约束自己的上边至位于position View的上边
     *
     * @param position view的position
     * @param offset   偏移量,与坐标轴同向
     */
    public Constraint topToTopOfView(int position, int offset) {

        setTopConstraint();
        top = mParent.getViewTop(position) + offset;
        return this;
    }


    /**
     * 约束自己的上边至位于position View的上边
     *
     * @param position view的position
     * @param offset   偏移量,与坐标轴同向
     * @param height   期望的高度
     */
    public Constraint topToTopOfView(int position, int offset, int height) {

        setTopConstraint();
        top = mParent.getViewTop(position) + offset;
        bottom = top + height;
        return this;
    }


    /**
     * 约束自己的上边至位于position View的下边
     *
     * @param position view的position
     * @param offset   偏移量,与坐标轴同向
     */
    public Constraint topToBottomOfView(int position, int offset) {

        setTopConstraint();
        top = mParent.getViewBottom(position) + offset;
        return this;
    }


    /**
     * 约束自己的上边至位于position View的下边,并指定高度
     *
     * @param position view的position
     * @param offset   偏移量,与坐标轴同向
     * @param height   期望的高度
     */
    public Constraint topToBottomOfView(int position, int offset, int height) {

        setTopConstraint();
        top = mParent.getViewBottom(position) + offset;
        bottom = top + height;
        return this;
    }


    /**
     * 约束自己的下边至位于position View的上边
     *
     * @param position view的position
     * @param offset   偏移量,与坐标轴同向
     */
    public Constraint bottomToTopOfView(int position, int offset) {

        setBottomConstraint();
        bottom = mParent.getViewTop(position) + offset;
        return this;
    }


    /**
     * 约束自己的下边至位于position View的上边,并指定高度
     *
     * @param position view的position
     * @param offset   偏移量,与坐标轴同向
     * @param height   期望的高度
     */
    public Constraint bottomToTopOfView(int position, int offset, int height) {

        setBottomConstraint();
        bottom = mParent.getViewTop(position) + offset;
        top = bottom - height;
        return this;
    }


    /**
     * 约束自己的下边至位于position View的下边
     *
     * @param position view的position
     * @param offset   偏移量,与坐标轴同向
     */
    public Constraint bottomToBottomOfView(int position, int offset) {

        setBottomConstraint();
        bottom = mParent.getViewBottom(position) + offset;
        return this;
    }


    /**
     * 约束自己的下边至位于position View的下边,并指定高度
     *
     * @param position view的position
     * @param offset   两条边之间的margin值
     * @param height   期望的高度
     */
    public Constraint bottomToBottomOfView(int position, int offset, int height) {

        setBottomConstraint();
        bottom = mParent.getViewBottom(position) + offset;
        top = bottom - height;
        return this;
    }

    //============================从一个view复制一个约束============================


    public Constraint copyFrom(int position) {

        setLeftConstraint();
        setTopConstraint();
        setRightConstraint();
        setBottomConstraint();

        left = mParent.getViewLeft(position);
        top = mParent.getViewTop(position);
        right = mParent.getViewRight(position);
        bottom = mParent.getViewBottom(position);
        return this;
    }


    /**
     * x方向平移约束
     *
     * @param offset 偏移量
     * @return 偏移后的约束
     */
    public Constraint translateX(int offset) {

        left += offset;
        right += offset;
        return this;
    }


    /**
     * 平移至坐标位置
     *
     * @param newX x位置,left将会移动到此,也会移动相同距离
     * @return 约束
     */
    public Constraint translateLeftTo(int newX) {

        int offset = newX - left;
        left = newX;
        right += offset;

        return this;
    }


    /**
     * 平移至坐标位置
     *
     * @param newX x位置,right将会移动到此,left也会移动相同距离
     * @return 约束
     */
    public Constraint translateRightTo(int newX) {

        int offset = newX - right;
        left += offset;
        right = newX;

        return this;
    }


    /**
     * y方向平移约束
     *
     * @param offset 偏移量
     * @return 偏移后的约束
     */
    public Constraint translateY(int offset) {

        top += offset;
        bottom += offset;
        return this;
    }


    /**
     * 平移至坐标位置
     *
     * @param newY y位置,top将会移动到此right,bottom也会移动相同距离
     * @return 约束
     */
    public Constraint translateTopTo(int newY) {

        int offset = newY - top;
        top = newY;
        bottom += offset;

        return this;
    }


    /**
     * 平移至坐标位置
     *
     * @param newY y位置,bottom将会移动到此,top也会移动相同距离
     * @return 约束
     */
    public Constraint translateBottomTo(int newY) {

        int offset = newY - bottom;
        top += offset;
        bottom = newY;

        return this;
    }


    /**
     * 移动约束
     *
     * @param leftOffset   左边约束移动偏移量
     * @param topOffset    上边约束移动偏移量
     * @param rightOffset  右边约束移动偏移量
     * @param bottomOffset 下边约束移动偏移量
     * @return 移动后的约束
     */
    public Constraint translate(int leftOffset, int topOffset, int rightOffset, int bottomOffset) {

        left += leftOffset;
        top += topOffset;
        right += rightOffset;
        bottom += bottomOffset;
        return this;
    }

    //============================生成spec============================


    /**
     * 检查该约束是否合法,合法:right>=left && bottom>=top
     */
    public void check(View view, int position) {

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();

        boolean hor;
        if (params.width == WRAP_CONTENT) {
            hor = true;
        } else {
            hor = right >= left;
        }

        boolean ver;
        if (params.height == WRAP_CONTENT) {
            ver = true;
        } else {
            ver = bottom >= top;
        }

        boolean legal = hor && ver;

        if (!legal) {
            String message = " right must >= left, bottom must >= top, current is: left=%d ," +
                    "top=%d ,right=%d , bottom=%d ; " + view.toString() + " layoutPosition: " + position;
            String format = String.format(Locale.CHINA, message, left, top, right, bottom);
            try {
                throw new RuntimeException(format);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 根据约束生成一个measureSpec
     *
     * @return 宽度Spec, 用于测量view
     */
    public int makeWidthSpec(View view) {

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        if (params.width == WRAP_CONTENT) {
            return View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 1, View.MeasureSpec.AT_MOST);
        } else {
            if (left < right) {
                return View.MeasureSpec.makeMeasureSpec(right - left, View.MeasureSpec.EXACTLY);
            } else {
                return View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY);
            }
        }
    }


    /**
     * 根据约束生成一个measureSpec
     *
     * @return 高度Spec, 用于测量view
     */
    public int makeHeightSpec(View view) {

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        if (params.height == WRAP_CONTENT) {
            return View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 1, View.MeasureSpec.AT_MOST);
        } else {
            if (bottom > top) {
                return View.MeasureSpec.makeMeasureSpec(bottom - top, View.MeasureSpec.EXACTLY);
            } else {
                return View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY);
            }
        }
    }

    //============================right bottom check============================


    /**
     * 如果父布局宽度是wrap_content,而你又约束到父布局右边,此时父布局的右边坐标是未知的;
     * 需要全部view测量之后才有坐标,而现在又需要坐标才能测量,矛盾
     */
    private void checkParentRight() {

        if (mParent.getParentRight() == -1) {
            throw new RuntimeException(" you can't constraint to parent right when parent width is not " +
                    "match_parent or a exactly dimensions ");
        }
    }


    /**
     * 如果父布局高度是wrap_content,而你又约束到父布局底边,此时父布局的底边坐标是未知的;
     * 需要全部view测量之后才有坐标,而现在又需要坐标才能测量,矛盾
     */
    private void checkParentBottom() {

        if (mParent.getParentBottom() == -1) {
            throw new RuntimeException(" you can't constraint to parent bottom when parent height is not " +
                    "match_parent or a exactly dimensions ");
        }
    }

    //============================weight support============================


    /**
     * 获取权重宽度,总宽度时父布局宽度
     *
     * @param base   总权重
     * @param weight 权重
     * @return 根据权重/总权重得到的宽度
     */
    public int getWeightWidth(int base, int weight) {

        return getWeightWidth(base, weight, 0);
    }


    /**
     * 获取权重宽度,会减去已经使用的宽度,在计算
     *
     * @param base      总权重
     * @param weight    权重
     * @param usedWidth 已经使用的宽度
     * @return 根据权重/总权重得到的宽度
     */
    public int getWeightWidth(int base, int weight, int usedWidth) {

        int parentRight = mParent.getParentRight();

        if (parentRight == -1) {
            throw new RuntimeException(" can't get weight Width, because width is not exactly ");
        }

        int useableWidth = parentRight - mParent.getParentLeft() - usedWidth;

        int cellWidth = (int) (useableWidth * 1f / base);

        return weight * cellWidth;
    }


    /**
     * 获取权重高度
     *
     * @param base   总权重
     * @param weight 权重
     * @return 根据权重/总权重得到的高度
     */
    public int getWeightHeight(int base, int weight) {

        return getWeightHeight(base, weight, 0);
    }


    /**
     * 获取权重宽度,会减去已经使用的宽度,在计算
     *
     * @param base       总权重
     * @param weight     权重
     * @param usedHeight 已经使用的高度
     * @return 根据权重/总权重得到的宽度
     */
    public int getWeightHeight(int base, int weight, int usedHeight) {

        int parentBottom = mParent.getParentBottom();

        if (parentBottom == -1) {
            throw new RuntimeException(" can't get weight Height, because Height is not exactly ");
        }

        int useableHeight = parentBottom - mParent.getParentTop() - usedHeight;

        int cellHeight = (int) (useableHeight * 1f / base) + 1;

        return weight * cellHeight;
    }

    //============================ size support ============================


    /**
     * 得到一个view的宽度
     *
     * @param position 布局位置
     * @return 该位置的view的宽度
     */
    public int getViewWidth(int position) {

        return mParent.getViewRight(position) - mParent.getViewLeft(position);
    }


    /**
     * 得到一个view的高度
     *
     * @param position 布局位置
     * @return 该位置的view的高度
     */
    public int getViewHeight(int position) {

        return mParent.getViewBottom(position) - mParent.getViewTop(position);
    }


    /**
     * 父布局宽度,没有padding值
     *
     * @return parent width
     */
    public int getParentWidth() {

        return mParent.getParentRight() - mParent.getParentLeft();
    }


    /**
     * 父布局高度,没有padding值
     *
     * @return parent height
     */
    public int getParentHeight() {

        return mParent.getParentBottom() - mParent.getParentTop();
    }

    //============================ view location support ============================


    /**
     * view's left
     *
     * @param position view layout position
     * @return view left
     */
    public int getViewLeft(int position) {

        return mParent.getViewLeft(position);
    }


    /**
     * view's top
     *
     * @param position layout position
     * @return view's top
     */
    public int getViewTop(int position) {

        return mParent.getViewTop(position);
    }


    /**
     * view's right
     *
     * @param position layout position
     * @return view's right
     */
    public int getViewRight(int position) {

        return mParent.getViewRight(position);
    }


    /**
     * view's bottom
     *
     * @param position layout position
     * @return view's bottom
     */
    public int getViewBottom(int position) {

        return mParent.getViewBottom(position);
    }

}
