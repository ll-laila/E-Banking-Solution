import { AdminComponentsModule } from './AdminComponents.module';

describe('ComponentsModule', () => {
  let componentsModule: AdminComponentsModule;

  beforeEach(() => {
    componentsModule = new AdminComponentsModule();
  });

  it('should create an instance', () => {
    expect(componentsModule).toBeTruthy();
  });
});
