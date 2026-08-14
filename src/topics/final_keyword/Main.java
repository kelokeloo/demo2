package topics.final_keyword;

public class Main {

    public static void main(String[] args) {
        System.out.println("===== 1. final 方法仍然可以被重载（重载和 final 无关） =====");
        Animal animal = new Animal();
        animal.eat();
        animal.eat("骨头");

        System.out.println();
        System.out.println("===== 2. 模板方法：final 固定流程，子类只能定制钩子方法 =====");
        Report base = new Report();
        base.render();
        System.out.println();
        SalesReport sales = new SalesReport();
        sales.render();

        System.out.println();
        System.out.println("===== 3. JDK 里的 final 方法 =====");
        System.out.println("Object.getClass() 是 final，任何类都无法重写它：");
        System.out.println("animal.getClass() = " + animal.getClass().getSimpleName());
    }

    static class Animal {
        public final void eat() {
            System.out.println("Animal.eat(): 动物在吃东西");
        }

        public void eat(String food) {
            System.out.println("Animal.eat(String): 动物在吃" + food);
        }
    }

    static class Report {
        public final void render() {
            System.out.println("===== 报告开始 =====");
            writeBody();
            System.out.println("===== 报告结束 =====");
        }

        protected void writeBody() {
            System.out.println("（默认正文：父类固定内容）");
        }
    }

    static class SalesReport extends Report {
        @Override
        protected void writeBody() {
            System.out.println("本月销售额：100 万元");
        }
    }
}
