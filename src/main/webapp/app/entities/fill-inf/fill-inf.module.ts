import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SchoolCardSharedModule } from 'app/shared';
import {
    FillInfComponent,
    FillInfDetailComponent,
    FillInfUpdateComponent,
    FillInfDeletePopupComponent,
    FillInfDeleteDialogComponent,
    fillInfRoute,
    fillInfPopupRoute
} from './';

const ENTITY_STATES = [...fillInfRoute, ...fillInfPopupRoute];

@NgModule({
    imports: [SchoolCardSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FillInfComponent,
        FillInfDetailComponent,
        FillInfUpdateComponent,
        FillInfDeleteDialogComponent,
        FillInfDeletePopupComponent
    ],
    entryComponents: [FillInfComponent, FillInfUpdateComponent, FillInfDeleteDialogComponent, FillInfDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SchoolCardFillInfModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
