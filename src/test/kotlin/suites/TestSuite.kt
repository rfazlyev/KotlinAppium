package suites

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import test.kotlin.ArticleTests
import test.kotlin.SearchTests

@RunWith(Suite::class)
@SuiteClasses(
    ArticleTests::class,
    SearchTests::class
)
class TestSuite {}