import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DigitopoSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [DigitopoSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [DigitopoSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DigitopoSharedModule {
  static forRoot() {
    return {
      ngModule: DigitopoSharedModule
    };
  }
}
