import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SchoolCardSharedModule } from 'app/shared';
import {
    AdminComponent,
    AdminDetailComponent,
    AdminUpdateComponent,
    AdminDeletePopupComponent,
    AdminDeleteDialogComponent,
    adminRoute,
    adminPopupRoute
} from './';

const ENTITY_STATES = [...adminRoute, ...adminPopupRoute];

@NgModule({
    imports: [SchoolCardSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AdminComponent, AdminDetailComponent, AdminUpdateComponent, AdminDeleteDialogComponent, AdminDeletePopupComponent],
    entryComponents: [AdminComponent, AdminUpdateComponent, AdminDeleteDialogComponent, AdminDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SchoolCardAdminModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
