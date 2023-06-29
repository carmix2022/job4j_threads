package ru.job4j.cache;

import org.junit.jupiter.api.Test;
import ru.job4j.CASCount;

import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    public void test1() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        base1.setName("A");
        cache.add(base1);
        base1.setName("a");
        cache.update(base1);
        assertThat(cache.get(base1.getId()).getName()).isEqualTo("a");
    }

    @Test
    public void test2() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        base1.setName("A");
        cache.add(base1);
        cache.update(base1);
        base1.setName("a");
        assertThatThrownBy(() -> cache.update(base1)).isInstanceOf(OptimisticException.class);
        assertThat(cache.get(base1.getId()).getName()).isEqualTo("A");
    }

    @Test
    public void test3() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        cache.add(base1);
        cache.delete(base1);
        assertThat(cache.add(base1)).isTrue();
    }

    @Test
    public void test4() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        cache.add(base1);
        Base base2 = new Base(1, 0);
        cache.delete(base2);
        assertThat(cache.get(1)).isNull();
    }
}