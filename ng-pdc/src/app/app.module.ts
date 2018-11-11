import { ApplicationRef, NgModule, NgZone } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ContractComponent } from './contract/contract.component';


@NgModule({
  declarations: [
    ContractComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [
  ],
  entryComponents: [
    ContractComponent
  ]
})
export class AppModule {
  constructor(
    private ngZone: NgZone,
    private applicationRef: ApplicationRef
  ) { }

  ngDoBootstrap() {
    window['bootstrapComponentContract'] = () => this.bootstrapComponent(ContractComponent);
  }

  bootstrapComponent(Component) {
    this.ngZone.run(() => this.applicationRef.bootstrap(Component));
  }

}
