package ge.tbc.tbcdemo.app

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ge.tbc.tbcdemo.app.scopes.PerActivity
import ge.tbc.tbcdemo.presentation.match.MatchActivity

@Module
abstract class ActivityBinderModule {

    @PerActivity
    @ContributesAndroidInjector
    abstract fun matchActivity(): MatchActivity
}