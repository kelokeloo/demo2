package topics.anonymous_inner_class;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 匿名内部类：没有名字，直接 new 一个接口/父类并当场实现其方法的类。
 * 最经典的用法是回调：把一段行为传给调用方，由对方在合适时机调用。
 */
public class Main {

    public static void main(String[] args) {
        // 1. 回调：点击行为在 Button 内部，但「点击后干什么」由我们通过匿名内部类传进去
        Button loginBtn = new Button("登录");
        loginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(Button button) {
                System.out.println("调用登录接口 /api/login，name = " + button.name);
            }
        });
        loginBtn.click();

        // 2. 对比 lambda：同一个回调，更简洁
        Button logoutBtn = new Button("退出");
        logoutBtn.setOnClickListener(button -> System.out.println("调用退出接口 /api/logout"));
        logoutBtn.click();

        // 3. Runnable：给线程传一段任务
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程执行");
            }
        }).start();

        // 4. Comparator：给排序传比较规则
        List<String> names = Arrays.asList("banana", "apple", "cherry");
        names.sort(new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.length() - b.length();
            }
        });
        System.out.println(names);
    }

    interface OnClickListener {
        void onClick(Button button);
    }

    static class Button {
        final String name;
        private OnClickListener listener;

        Button(String name) {
            this.name = name;
        }

        void setOnClickListener(OnClickListener listener) {
            this.listener = listener;
        }

        void click() {
            if (listener != null) {
                listener.onClick(this); // 在合适时机回调
            }
        }
    }
}
